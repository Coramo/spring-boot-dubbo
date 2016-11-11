package me.coramo.springframework.boot.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wcx on 16/10/31.
 */
public class DubboRunner implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DubboRunner.class);

  private AtomicBoolean running = new AtomicBoolean(false);

  private Thread runner = new Thread("DubboLifecycle") {
    {
      setDaemon(false);
      setDefaultUncaughtExceptionHandler((t, e) -> {
        LOGGER.error("Abnormal thread termination",e);
      });
    }

    @Override
    public void run() {
      if (running.compareAndSet(false,true)) {
        LOGGER.info("Started DubboRunner");
        while (running.get()) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            LOGGER.error("An unexpected exceptions.",e);
          }
        }
        LOGGER.info("Stopped DubboRunner");
      }
    }
  };

  @Override
  public void run(String... strings) throws Exception {
    runner.start();
  }

  @Override
  public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
    running.set(false);
  }
}
