## Comic Pictures APIs

### 1. Get the List of Comic Pictures

#### URL

* Get all categories of pictures :

  `https://rabtman.com/api/v2/acgclub/pictures`

* Get pictures of the specified category :

  `https://rabtman.com/api/v2/acgclub/category/{type}/pictures`

#### Supported Formats

> JSON

#### HTTP Request Method

> GET

#### Request Parameters

| Parameters | Required | Type   | Description                                                  |
| :--------- | :------- | :----- | ------------------------------------------------------------ |
| {type}     | false    | string | path parameter, which gets the picture of the specified type; currently supported categories: `moeimg`, `cosplay`, `gamersky` |
| offset     | false    | int    | the requested page number, default: 1|
|limit | false | int | number of results returned per page, default: 20, value range: 20~60 |
| query | false | string | search keywords |

#### Response Fields

| Response Field | Field Type    | Description                     |
| :----------- | :------------ | :------------------------------ |
| title        | string        | picture 's name                 |
| type         | string        | picture belongs to category   |
| thumbnail    | string        | picture 's thumbnail         |
| sort         | string or int | serial number of pictures |
| imgUrls      | array         | the list of pictures under this theme |

#### API Example

> Address: [https://rabtman.com/api/v2/acgclub/pictures/category/gamersky?query=%E8%BF%9C%E5%9D%82%E5%87%9B)
```json
{
	"message": "请求成功",
	"data": [{
		"thumbnail": "http://imgs.gamersky.com/upimg/2018/201802031433291315.jpg",
		"title": "《Fate/Stay night》官方公布远坂凛生日壁纸",
		"type": "gamersky",
		"sort": 1010964,
		"imgUrls": [
			"http://img1.gamersky.com/image2018/02/20180203_ljt_220_5/gamersky_01origin_01_201823142764B.jpg",
			"http://img1.gamersky.com/image2018/02/20180203_ljt_220_5/gamersky_02origin_03_2018231427A25.jpg"
		]
	}]
}
```

#### Other Instructions

The pictures are updated once a day, at about 5 am, and it is recommended to do the appropriate caching.

## Appendix

### Basic Response Fields

| Response Field | Field Type      | Description                                             |
| :------------- | :-------------- | :------------------------------------------------------ |
| message        | string          | response information                                    |
| data           | object or array | the result of the response, possibly an object or array |

### Request Restrictions

To reduce server pressure, the frequency of requests is limited.

The current API request limit is: `30/minute`, `3/second`

### Status Code

| Status Code | Description          |
| ----------- | -------------------- |
| 200         | request success      |
| 400         | parameter exceptions |
| 404         | page not found       |
| 500         | server exceptions    |
