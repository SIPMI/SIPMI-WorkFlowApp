package models;


import java.util.concurrent.TimeUnit;

import controllers.TaskActor;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import scala.concurrent.duration.FiniteDuration;

public class Global extends GlobalSettings{

	private akka.actor.Cancellable canceller = null;

	@Override
	public void onStart(Application app){
		//アプリ起動時に行う処理
		Logger.info("sipmi app start!!");

		super.onStart(app);

        akka.actor.ActorRef actor = play.libs.Akka.system().actorOf(
            akka.actor.Props.create(TaskActor.class)
        );
        canceller = play.libs.Akka.system().scheduler().schedule(
            FiniteDuration.create( 5, TimeUnit.SECONDS), // スケジュール開始時点から初回実行までの時間.
            FiniteDuration.create(10, TimeUnit.SECONDS), // 2回目以降の実行間隔.
            actor,
            "N/A", // onReceiveの第1引数に渡る値. nullだとジョブ実行時に例外が飛ぶ.
            play.libs.Akka.system().dispatcher(),
            null
        );

	}

	@Override
	public void onStop(Application app){
		//アプリ終了時に行う処理
		Logger.info("sipmi app stop!!");

		if(canceller != null) {
            canceller.cancel();
        }

        super.onStop(app);
	}


//	public Promise<SimpleResult> onError(RequestHeader request, Throwable t) {
//        return Promise.<SimpleResult>pure(internalServerError(
//            views.html.errorPage.render(t)
//        ));
//    }
//
//	public Promise<SimpleResult> onHandlerNotFound(RequestHeader request) {
//        return Promise.<SimpleResult>pure(notFound(
//            views.html.notFoundPage.render(request.uri())
//        ));
//    }
//
//	 public Promise<SimpleResult> onBadRequest(RequestHeader request, String error) {
//	    return Promise.<SimpleResult>pure(badRequest("Don't try to hack the URI!"));
//	 }

}
