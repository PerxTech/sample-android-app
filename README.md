# Loading the game in a native app
This proposes a simple way of loading the game in a native app.

## Accessing the game
Load the game in a webview using the following url
```
<base_url>?pi=<user_id>&campaignId=<campaign_id>
```
For instance:
```
https://microsite-digi-minigames.perxtech.io/games?pi=0004&campaignId=239
```

Note, the web app makes use of javascrit and local storage for authentication. So, those 2 should be activated.

In android, this is done by doing

```java
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
```

## Getting payload after game is completed
Once the game completes, the current url will change to 
```
<base_url>/result?payload=<payload>
```

So, the native app should listen for any url change to capture the completion event.

The "\<payload\>" query parameter is the actual json payload coming from the backend encoded in base64.