# Loading the game in a native app
This proposes a simple way of loading the game in a native app.

## Accessing the game
Load the game in a webview using the following url
```
<base_url>?pi=<user_id>&campaignId=<campaign_id>?token=<token>
```
For instance:
```
https://microsite-minigames.perxtech.io/games?pi=0004&campaignId=239&token=432423324dsvfsd2134f
```

Note, the web app makes use of javascript and local storage for authentication. So, those 2 should be activated.

In android, this is done by doing

```java
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
```
