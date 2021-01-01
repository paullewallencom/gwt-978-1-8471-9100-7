package com.packtpub.gwtbook.samples.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.samples.client.LiveSearchService;

      public class PrimesServiceImpl extends RemoteServiceServlet
                                            implements PrimesService 
      {
        private static final long serialVersionUID = 
                                               -8620968747002510678L;
        public boolean isPrimeNumber(int numberToVerify) 
        {
          boolean isPrime = true;
          int limit = (int) Math.sqrt ( numberToVerify );  
          for ( int i = 2; i <= limit; i++ )
            {
              if(numberToVerify % i == 0 )
              {
                isPrime = false;
                break;
              }
            }
          return isPrime;
          }
        }

