package com.packtpub.gwtbook.samples.server;

import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.gwtbook.samples.client.AutoFormFillService;

public class LiveSearchServiceImpl extends RemoteServiceServlet
implements LiveSearchService
{
private String[] items = new String[]
{"apple", "peach", "orange", "banana", "plum", "avocado",
"strawberry", "pear", "watermelon", "pineapple", "grape",
"blueberry", "cantaloupe"
};
public List getCompletionItems(String itemToMatch)
{
ArrayList completionList = new ArrayList();
for (int i = 0; i < items.length; i++)
{
if (items[i].startsWith(itemToMatch.toLowerCase()))
{
completionList.add(items[i]);
}
}
return completionList;
}
}
