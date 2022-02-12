package com.seg;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.stage.Stage;

public class ApplicationFx extends Application{         

    private static ConfigurableApplicationContext contexto;
    
    @Override
    public void init() throws Exception {         
        notifyPreloader(new ProgressNotification(0));       
        contexto = SpringApplication.run(Main.class);                                
//        springContexto = new SpringApplicationBuilder(SpringAplicacion.class).run();
    }
    
    @Override
    public void start(Stage stage) throws Exception {        
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));               
    }
       
    @Override
    public void stop() throws Exception {
        super.stop();
        contexto.close();        
    }               
    
    public static ConfigurableApplicationContext getContexto(){
        return contexto;
    }
}

