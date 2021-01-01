package com.packtpub.gwtbook.samples.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

       public interface PrimesService extends RemoteService 
       {
         public boolean isPrimeNumber(int numberToVerify);
       }

