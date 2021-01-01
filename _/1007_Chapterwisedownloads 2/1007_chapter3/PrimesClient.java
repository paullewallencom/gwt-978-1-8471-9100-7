package com.packtpub.gwtbook.samples.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public class PrimesClient implements EntryPoint 
          {
          
            public void onModuleLoad() 
            {
              final TextBox primeNumber = new TextBox();
            }
          final PrimesServiceAsync primesService = 
                                             (PrimesServiceAsync) GWT
          create(PrimesService.class);
          ServiceDefTarget endpoint = 
                                    (ServiceDefTarget) primesService;
          endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() +
                                                           “primes”);
final Button checkPrime=new Button("Is this a prime number?",
                                                 new ClickListener())
    {
      public void onClick(Widget sender) 
      {
        AsyncCallback callback = new AsyncCallback() 
        {
          public void onSuccess(Object result) 
          {
            if(((Boolean) result).booleanValue()) 
            {
              Window.alert("Yes, "+ primeNumber.getText()
                                           + "' is a prime number.");
            } 
            else 
            {
              Window.alert("No, "+ primeNumber.getText()
                                       + "' is not a prime number.");
            }
          }
          public void onFailure(Throwable caught) 
          {
            Window.alert("Error while calling the Primes
                                                          Service.");
          }
            };
          primesService.isPrimeNumber(Integer
                          parseInt(primeNumber.getText()), callback);
       }
     });
}