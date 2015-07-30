package com.kodebeagle.crawler

import java.io.File
import java.net.URL


import akka.actor.{PoisonPill, ActorSystem, Props, Actor}
import com.kodebeagle.logging.Logger

import org.apache.commons.io.FileUtils
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


class GitHubRepoDownloadTracker extends Actor with Logger {
  import GitHubRepoDownloadTracker._

  val child = context.actorOf(Props[MyScheduler])
  def receive: PartialFunction[Any, Unit] = {
    case RepoDownloadTracker(repoFile, url) =>
      try {
        log.info(s"downloader : downloading filename : $repoFile")
        scala.concurrent.Future(FileUtils.copyURLToFile(url, repoFile))
        child ! TrackRepoDownload(repoFile, url)
      } catch {
        case x: Throwable =>
          log.error(s"Failed to download file", x)
          None
      }
  }
}

class MyScheduler extends Actor with Logger {
  import GitHubRepoDownloadTracker._

  val cancellable =
    system.scheduler.schedule(0 minutes,
      10 milliseconds,
      self,
      TrackRepoDownload)

  def trackRepoDownload(repoFile: File, url: URL): Unit = {
    log.info(s"scheduler : downloading filename : $repoFile")
    if (!repoFile.exists()){
      sender ! RepoDownloadTracker(repoFile, url) //This may create loop.
    } else{
      log.info(s"scheduler : downloaded filename : $repoFile closing scheduler")
      cancellable.cancel()
      self ! PoisonPill
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case TrackRepoDownload(file, repoUrl) =>
      trackRepoDownload(file, repoUrl)
  }
}

object GitHubRepoDownloadTracker {
  case class RepoDownloadTracker(repoFile: File, url: URL)
  case class TrackRepoDownload(repoFile: File, url :URL)
  val system = ActorSystem("RepoDownloadTracker")
  def createDownloadTracker() = system.actorOf(Props[GitHubRepoDownloadTracker])
}
