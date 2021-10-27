package com.learncamel.route;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.learncamel.bean.MyBean;

@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
    	from("timer:hello?period=10s")
    	   	.pollEnrich("file:data/input?delete=true&readLock=none")
        	.recipientList(constant("direct:start,direct:tap,direct:d"))
    	   	.recipientList(header("camelfileName")).streaming().parallelProcessing().ignoreInvalidEndpoints()
    	   	
    		.to("direct:start");
    	
    	
    	  //from("vm:start")
    	  from("direct-vm:start")
    	  .to("direct:start1")
          .log("Main route: Send '${body}' to tap router")
          .wireTap("direct:tap")
          .log("Main route: Add 'two' to '${body}'")
          .bean(MyBean.class, "addTwo")
          .log("Main route: Output '${body}'");

          from("direct:tap")
          .log("Tap Wire route: received '${body}'")
          .log("Tap Wire route: Add 'three' to '${body}'")
          .bean(MyBean.class, "addThree")
          .log("Tap Wire route: Output '${body}'");
          
          from("direct:d")
          .log("Main route: Send '${body}' to tap router")
          .wireTap("direct:tap")
          .log("Main route: Add 'two' to '${body}'")
          .bean(MyBean.class, "addTwo")
          .log("Main route: Output '${body}'");

          
          
          from("ftp://foo@myserver?password=secret&ftpClientConfig=#myConfig").threads(10, 100).to("file://data/output");

          from("ftp://foo@myserver?password=secret&ftpClientConfig=#myConfig")
          	.to("bean:foo").to("file://data/output");

          
          from("direct:foo"). loadBalance().failover(Exception.class).to("direct:a") .to("direct:b");
          
          from("direct:foo").loadBalance().failover(-1, false, true) .to("direct:bad") .to("direct:bad2").to("direct:good") .to("direct:good2"); 

          //inflight request/message:
        	  
        	  
          String distributionRatio = "4,2,1"; // round-robin 
        		  
        from("direct:start") .loadBalance().weighted(true, distributionRatio) .to("mock:x", "mock:y", "mock:z"); //random from("direct:start") .loadBalance().weighted(false, distributionRatio) .to("mock:x", "mock:y", "mock:z"); 

        		  
          
    }
}
