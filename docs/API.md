# Java Client API Reference

## Create Obstor Client.

## Obstor

```java
ObstorClient obstorClient =
    ObstorClient.builder()
        .endpoint("https://demo.obstor.net")
        .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
        .build();
```

## AWS S3

```java
ObstorClient obstorClient =
    ObstorClient.builder()
        .endpoint("https://s3.amazonaws.com")
        .credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
        .build();
```

| Bucket operations                                                 | Object operations                                       |
|-------------------------------------------------------------------|---------------------------------------------------------|
| [`bucketExists`](#bucketExists)                                   | [`composeObject`](#composeObject)                       |
| [`deleteBucketCors`](#deleteBucketCors)                           | [`copyObject`](#copyObject)                             |
| [`deleteBucketEncryption`](#deleteBucketEncryption)               | [`deleteObjectTags`](#deleteObjectTags)                 |
| [`deleteBucketLifecycle`](#deleteBucketLifecycle)                 | [`disableObjectLegalHold`](#disableObjectLegalHold)     |
| [`deleteBucketNotification`](#deleteBucketNotification)           | [`downloadObject`](#downloadObject)                     |
| [`deleteBucketPolicy`](#deleteBucketPolicy)                       | [`enableObjectLegalHold`](#enableObjectLegalHold)       |
| [`deleteBucketReplication`](#deleteBucketReplication)             | [`getObject`](#getObject)                               |
| [`deleteBucketTags`](#deleteBucketTags)                           | [`getObjectAcl`](#getObjectAcl)                         |
| [`deleteObjectLockConfiguration`](#deleteObjectLockConfiguration) | [`getObjectAttributes`](#getObjectAttributes)           |
| [`getBucketCors`](#getBucketCors)                                 | [`getObjectRetention`](#getObjectRetention)             |
| [`getBucketEncryption`](#getBucketEncryption)                     | [`getObjectTags`](#getObjectTags)                       |
| [`getBucketLifecycle`](#getBucketLifecycle)                       | [`getPresignedObjectUrl`](#getPresignedObjectUrl)       |
| [`getBucketNotification`](#getBucketNotification)                 | [`getPresignedPostFormData`](#getPresignedPostFormData) |
| [`getBucketPolicy`](#getBucketPolicy)                             | [`isObjectLegalHoldEnabled`](#isObjectLegalHoldEnabled) |
| [`getBucketReplication`](#getBucketReplication)                   | [`listObjects`](#listObjects)                           |
| [`getBucketTags`](#getBucketTags)                                 | [`promptObject`](#promptObject)                         |
| [`getBucketVersioning`](#getBucketVersioning)                     | [`putObject`](#putObject)                               |
| [`getObjectLockConfiguration`](#getObjectLockConfiguration)       | [`putObjectFanOut`](#putObjectFanOut)                   |
| [`listBuckets`](#listBuckets)                                     | [`removeObject`](#removeObject)                         |
| [`listenBucketNotification`](#listenBucketNotification)           | [`removeObjects`](#removeObjects)                       |
| [`makeBucket`](#makeBucket)                                       | [`restoreObject`](#restoreObject)                       |
| [`removeBucket`](#removeBucket)                                   | [`selectObjectContent`](#selectObjectContent)           |
| [`setBucketCors`](#setBucketCors)                                 | [`setObjectRetention`](#setObjectRetention)             |
| [`setBucketEncryption`](#setBucketEncryption)                     | [`setObjectTags`](#setObjectTags)                       |
| [`setBucketLifecycle`](#setBucketLifecycle)                       | [`statObject`](#statObject)                             |
| [`setBucketNotification`](#setBucketNotification)                 | [`uploadObject`](#uploadObject)                         |
| [`setBucketPolicy`](#setBucketPolicy)                             | [`uploadSnowballObjects`](#uploadSnowballObjects)       |
| [`setBucketReplication`](#setBucketReplication)                   |                                                         |
| [`setBucketTags`](#setBucketTags)                                 |                                                         |
| [`setBucketVersioning`](#setBucketVersioning)                     |                                                         |
| [`setObjectLockConfiguration`](#setObjectLockConfiguration)       |                                                         |

## 1. Obstor Client Builder

Obstor Client Builder is used to create Obstor client. Builder has below methods to accept arguments.
| Method          | Description                                                                                                                                |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| `endpoint()`    | Accepts endpoint as a String, URL or okhttp3.HttpUrl object and optionally accepts port number and flag to enable secure (TLS) connection. |
|                 | Endpoint as a string can be formatted like below:                                                                                          |
|                 | `https://s3.amazonaws.com`                                                                                                                 |
|                 | `https://demo.obstor.net`                                                                                                                      |
|                 | `https://demo.obstor.net:9000`                                                                                                                 |
|                 | `localhost`                                                                                                                                |
|                 | `demo.obstor.net`                                                                                                                              |
| `credentials()` | Accepts access key (aka user ID) and secret key (aka password) of an account in S3 service.                                                |
| `region()`      | Accepts region name of S3 service. If specified, all operations use this region otherwise region is probed per bucket.                     |
| `httpClient()`  | Custom HTTP client to override default.                                                                                                    |

__Examples__

### Obstor

```java
// 1. Create client to S3 service 'demo.obstor.net' at port 443 with TLS security
// for anonymous access.
ObstorClient obstorClient = ObstorClient.builder().endpoint("https://demo.obstor.net").build();

// 2. Create client to S3 service 'demo.obstor.net' at port 443 with TLS security
// using URL object for anonymous access.
ObstorClient obstorClient = ObstorClient.builder().endpoint(new URL("https://demo.obstor.net")).build();

// 3. Create client to S3 service 'demo.obstor.net' at port 9000 with TLS security
// using okhttp3.HttpUrl object for anonymous access.
ObstorClient obstorClient =
    ObstorClient.builder().endpoint(HttpUrl.parse("https://demo.obstor.net:9000")).build();

// 4. Create client to S3 service 'demo.obstor.net' at port 443 with TLS security
// for authenticated access.
ObstorClient obstorClient =
    ObstorClient.builder()
	    .endpoint("https://demo.obstor.net")
		.credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
		.build();

// 5. Create client to S3 service 'demo.obstor.net' at port 9000 with non-TLS security
// for authenticated access.
ObstorClient obstorClient =
    ObstorClient.builder()
	    .endpoint("demo.obstor.net", 9000, false)
	    .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
		.build();

// 6. Create client to S3 service 'demo.obstor.net' at port 9000 with TLS security
// for authenticated access.
ObstorClient obstorClient =
    ObstorClient.builder()
	    .endpoint("demo.obstor.net", 9000, true)
		.credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
		.build();

// 7. Create client to S3 service 'demo.obstor.net' at port 443 with TLS security
// and region 'us-west-1' for authenticated access.
ObstorClient obstorClient =
    ObstorClient.builder()
	    .endpoint(new URL("https://demo.obstor.net"))
		.credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
		.region("us-west-1")
		.build();

// 8. Create client to S3 service 'demo.obstor.net' at port 9000 with TLS security,
// region 'eu-east-1' and custom HTTP client for authenticated access.
ObstorClient obstorClient =
    ObstorClient.builder()
	    .endpoint("https://demo.obstor.net:9000")
		.credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
		.region("eu-east-1")
		.httpClient(customHttpClient)
		.build();
```

### AWS S3

```java
// 1. Create client to S3 service 's3.amazonaws.com' at port 443 with TLS security
// for anonymous access.
ObstorClient s3Client = ObstorClient.builder().endpoint("https://s3.amazonaws.com").build();

// 2. Create client to S3 service 's3.amazonaws.com' at port 443 with TLS security
// using URL object for anonymous access.
ObstorClient s3Client = ObstorClient.builder().endpoint(new URL("https://s3.amazonaws.com")).build();

// 3. Create client to S3 service 's3.amazonaws.com' at port 9000 with TLS security
// using okhttp3.HttpUrl object for anonymous access.
ObstorClient s3Client =
    ObstorClient.builder().endpoint(HttpUrl.parse("https://s3.amazonaws.com")).build();

// 4. Create client to S3 service 's3.amazonaws.com' at port 80 with TLS security
// for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
	    .endpoint("s3.amazonaws.com")
		.credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.build();

// 5. Create client to S3 service 's3.amazonaws.com' at port 443 with non-TLS security
// for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
        .endpoint("s3.amazonaws.com", 433, false)
		.credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.build();

// 6. Create client to S3 service 's3.amazonaws.com' at port 80 with non-TLS security
// for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
	    .endpoint("s3.amazonaws.com", 80, false)
        .credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.build();

// 7. Create client to S3 service 's3.amazonaws.com' at port 80 with TLS security
// for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
	    .endpoint("s3.amazonaws.com", 80, true)
        .credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.build();

// 8. Create client to S3 service 's3.amazonaws.com' at port 80 with non-TLS security
// and region 'us-west-1' for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
	    .endpoint("s3.amazonaws.com", 80, false)
        .credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.region("us-west-1")
		.build();

// 9. Create client to S3 service 's3.amazonaws.com' at port 443 with TLS security
// and region 'eu-west-2' for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
	    .endpoint("s3.amazonaws.com", 443, true)
		.credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.region("eu-west-2")
		.build();

// 10. Create client to S3 service 's3.amazonaws.com' at port 443 with TLS security,
// region 'eu-central-1' and custom HTTP client for authenticated access.
ObstorClient s3Client =
    ObstorClient.builder()
	    .endpoint("s3.amazonaws.com", 443, true)
        .credentials("YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY")
		.region("eu-central-1")
		.httpClient(customHttpClient)
		.build();
```

## Common Exceptions
All APIs throw below exceptions in addition to specific to API.

| Exception                 | Cause                                                                |
|:--------------------------|:---------------------------------------------------------------------|
| ErrorResponseException    | Thrown to indicate S3 service returned an error response.            |
| IllegalArgumentException  | Throws to indicate invalid argument passed.                          |
| InsufficientDataException | Thrown to indicate not enough data available in InputStream.         |
| InternalException         | Thrown to indicate internal library error.                           |
| InvalidKeyException       | Thrown to indicate missing of HMAC SHA-256 library.                  |
| InvalidResponseException  | Thrown to indicate S3 service returned invalid or no error response. |
| IOException               | Thrown to indicate I/O error on S3 operation.                        |
| NoSuchAlgorithmException  | Thrown to indicate missing of MD5 or SHA-256 digest library.         |
| ServerException           | Thrown to indicate HTTP server error.                                |
| XmlParserException        | Thrown to indicate XML parsing error.                                |

## 2. Bucket operations

<a name="bucketExists"></a>
### bucketExists(BucketExistsArgs args)
`public boolean bucketExists(BucketExistsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#bucketExists-net.obstor.BucketExistsArgs-)_

Checks if a bucket exists.

__Parameters__
| Parameter      | Type                 | Description    |
|:---------------|:---------------------|:---------------|
| ``bucketName`` | _[BucketExistsArgs]_ | Arguments.     |

| Returns                                |
|:---------------------------------------|
| _boolean_ - True if the bucket exists. |

__Example__
```java
// Check whether 'my-bucketname' exists or not.
boolean found =
  obstorClient.bucketExists(BucketExistsArgs.builder().bucket("my-bucketname").build());
if (found) {
  System.out.println("my-bucketname exists");
} else {
  System.out.println("my-bucketname does not exist");
}
```

<a name="deleteBucketCors"></a>
### deleteBucketCors(DeleteBucketCorsArgs args)
`private void deleteBucketCors(DeleteBucketCorsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketCors-net.obstor.DeleteBucketCorsArgs-)_

Deletes CORS configuration of a bucket.

__Parameters__
| Parameter | Type                     | Description |
|:----------|:-------------------------|:------------|
| ``args``  | _[DeleteBucketCorsArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketCors(DeleteBucketCorsArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteBucketEncryption"></a>
### deleteBucketEncryption(DeleteBucketEncryptionArgs args)
`private void deleteBucketEncryption(DeleteBucketEncryptionArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketEncryption-net.obstor.DeleteBucketEncryptionArgs-)_

Deletes encryption configuration of a bucket.

__Parameters__
| Parameter | Type                           | Description |
|:----------|:-------------------------------|:------------|
| ``args``  | _[DeleteBucketEncryptionArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketEncryption(
    DeleteBucketEncryptionArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteBucketLifecycle"></a>
### deleteBucketLifecycle(DeleteBucketLifecycleArgs args)
`private void deleteBucketLifecycle(DeleteBucketLifecycleArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketLifecycle-net.obstor.DeleteBucketLifecycleArgs-)_

Deletes lifecycle configuration of a bucket.

__Parameters__
| Parameter | Type                          | Description |
|:----------|:------------------------------|:------------|
| ``args``  | _[DeleteBucketLifecycleArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketLifecycle(
    DeleteBucketLifecycleArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteBucketTags"></a>
### deleteBucketTags(DeleteBucketTagsArgs args)
`private void deleteBucketTags(DeleteBucketTagsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketTags-net.obstor.DeleteBucketTagsArgs-)_

Deletes tags of a bucket.

__Parameters__
| Parameter | Type                     | Description |
|:----------|:-------------------------|:------------|
| ``args``  | _[DeleteBucketTagsArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketTags(DeleteBucketTagsArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteBucketPolicy"></a>
### deleteBucketPolicy(DeleteBucketPolicyArgs args)
`private void deleteBucketPolicy(DeleteBucketPolicyArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketPolicy-net.obstor.DeleteBucketPolicyArgs-)_

Deletes bucket policy configuration of a bucket.

__Parameters__
| Parameter | Type                       | Description |
|:----------|:---------------------------|:------------|
| ``args``  | _[DeleteBucketPolicyArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteBucketReplication"></a>
### deleteBucketReplication(DeleteBucketReplicationArgs args)
`private void deleteBucketReplication(DeleteBucketReplicationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketReplication-net.obstor.DeleteBucketReplicationArgs-)_

Deletes bucket replication configuration of a bucket.

__Parameters__
| Parameter | Type                            | Description |
|:----------|:--------------------------------|:------------|
| ``args``  | _[DeleteBucketReplicationArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketReplication(
    DeleteBucketReplicationArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteBucketNotification"></a>
### deleteBucketNotification(DeleteBucketNotificationArgs args)
`public void deleteBucketNotification(DeleteBucketNotificationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteBucketNotification-net.obstor.DeleteBucketNotificationArgs-)_

Deletes notification configuration of a bucket.

__Parameters__
| Parameter | Type                             | Description |
|:----------|:---------------------------------|:------------|
| ``args``  | _[DeleteBucketNotificationArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteBucketNotification(
    DeleteBucketNotificationArgs.builder().bucket("my-bucketname").build());
```

<a name="deleteObjectLockConfiguration"></a>
### deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs args)
`public void deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteObjectLockConfiguration-net.obstor.DeleteObjectLockConfigurationArgs-)_

Deletes object-lock configuration in a bucket.

__Parameters__
| Parameter | Type                                  | Description |
|:----------|:--------------------------------------|:------------|
| ``args``  | _[DeleteObjectLockConfigurationArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteObjectLockConfiguration(
    DeleteObjectLockConfigurationArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketCors"></a>
### getBucketCors(GetBucketCorsArgs args)
`public Tags getBucketCors(GetBucketCorsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.htmlgetBucketCors-net.obstor.GetBucketCorsArgs-)_

Gets CORS configuration of a bucket.

__Parameters__
| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[GetBucketCorsArgs]_ | Arguments.  |


| Returns                                     |
|:--------------------------------------------|
| _[CORSConfiguration]_ - CORS configuration. |

__Example__
```java
CORSConfiguration config = obstorClient.getBucketCors(GetBucketCorsArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketEncryption"></a>
### getBucketEncryption(GetBucketEncryptionArgs args)
`public SseConfiguration getBucketEncryption(GetBucketEncryptionArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getBucketEncryption-net.obstor.GetBucketEncryptionArgs-)_

Gets encryption configuration of a bucket.

__Parameters__
| Parameter | Type                        | Description |
|:----------|:----------------------------|:------------|
| ``args``  | _[GetBucketEncryptionArgs]_ | Arguments.  |

| Returns                                                      |
|:-------------------------------------------------------------|
| _[SseConfiguration]_ - Server-side encryption configuration. |

__Example__
```java
SseConfiguration config =
    obstorClient.getBucketEncryption(
        GetBucketEncryptionArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketLifecycle"></a>
### getBucketLifecycle(GetBucketLifecycleArgs args)
`public LifecycleConfiguration getBucketLifecycle(GetBucketLifecycleArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getBucketLifecycle-net.obstor.GetBucketLifecycleArgs-)_

Gets lifecycle configuration of a bucket.

__Parameters__
| Parameter | Type                       | Description |
|:----------|:---------------------------|:------------|
| ``args``  | _[GetBucketLifecycleArgs]_ | Arguments.  |

| Returns                                               |
|:------------------------------------------------------|
| _[LifecycleConfiguration]_ - lifecycle configuration. |

__Example__
```java
LifecycleConfiguration config =
    obstorClient.getBucketLifecycle(
	    GetBucketLifecycleArgs.builder().bucket("my-bucketname").build());
System.out.println("Lifecycle configuration: " + config);
```

<a name="getBucketNotification"></a>
### getBucketNotification(GetBucketNotificationArgs args)
`public NotificationConfiguration getBucketNotification(GetBucketNotificationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getBucketNotification-net.obstor.GetBucketNotificationArgs-)_

Gets notification configuration of a bucket.

__Parameters__
| Parameter | Type                          | Description |
|:----------|:------------------------------|:------------|
| ``args``  | _[GetBucketNotificationArgs]_ | Arguments.  |

| Returns                                                     |
|:------------------------------------------------------------|
| _[NotificationConfiguration]_ - Notification configuration. |

__Example__
```java
NotificationConfiguration config =
    obstorClient.getBucketNotification(
	    GetBucketNotificationArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketPolicy"></a>
### getBucketPolicy(GetBucketPolicyArgs args)
`public String getBucketPolicy(GetBucketPolicyArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getBucketPolicy-net.obstor.GetBucketPolicyArgs-)_

Gets bucket policy configuration of a bucket.

__Parameters__
| Parameter | Type                    | Description |
|:----------|:------------------------|:------------|
| ``args``  | _[GetBucketPolicyArgs]_ | Arguments.  |


| Returns                                                |
|:-------------------------------------------------------|
| _String_ - Bucket policy configuration as JSON string. |

__Example__
```java
String config =
    obstorClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketReplication"></a>
### getBucketReplication(GetBucketReplicationArgs args)
`public ReplicationConfiguration getBucketReplication(GetBucketReplicationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getBucketReplication-net.obstor.GetBucketReplicationArgs-)_

Gets bucket replication configuration of a bucket.

__Parameters__
| Parameter | Type                         | Description |
|:----------|:-----------------------------|:------------|
| ``args``  | _[GetBucketReplicationArgs]_ | Arguments.  |


| Returns                                                          |
|:-----------------------------------------------------------------|
| _[ReplicationConfiguration]_ - Bucket replication configuration. |

__Example__
```java
ReplicationConfiguration config =
    obstorClient.getBucketReplication(
	    GetBucketReplicationArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketTags"></a>
### getBucketTags(GetBucketTagsArgs args)
`public Tags getBucketTags(GetBucketTagsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.htmlgetBucketTags-net.obstor.GetBucketTagsArgs-)_

Gets tags of a bucket.

__Parameters__
| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[GetBucketTagsArgs]_ | Arguments.  |


| Returns          |
|:-----------------|
| _[Tags]_ - tags. |

__Example__
```java
Tags tags = obstorClient.getBucketTags(GetBucketTagsArgs.builder().bucket("my-bucketname").build());
```

<a name="getBucketVersioning"></a>
### getBucketVersioning(GetBucketVersioningArgs args)
`public VersioningConfiguration getBucketVersioning(GetBucketVersioningArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getBucketVersioning-net.obstor.GetBucketVersioningArgs-)_

Gets versioning configuration of a bucket.

__Parameters__
| Parameter | Type                        | Description |
|:----------|:----------------------------|:------------|
| ``args``  | _[GetBucketVersioningArgs]_ | Arguments.  |

| Returns                                                 |
|:--------------------------------------------------------|
| _[VersioningConfiguration]_ - Versioning configuration. |

__Example__
```java
VersioningConfiguration config =
    obstorClient.getBucketVersioning(
        GetBucketVersioningArgs.builder().bucket("my-bucketname").build());
```

<a name="getObjectLockConfiguration"></a>
### getObjectLockConfiguration(GetObjectLockConfigurationArgs args)
`public ObjectLockConfiguration getObjectLockConfiguration(GetObjectLockConfigurationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObjectLockConfiguration-net.obstor.GetObjectLockConfigurationArgs-)_

Gets object-lock configuration in a bucket.

__Parameters__

| Parameter | Type                               | Description |
|:----------|:-----------------------------------|:------------|
| ``args``  | _[GetObjectLockConfigurationArgs]_ | Arguments.  |

| Returns                                                        |
|:---------------------------------------------------------------|
| _[ObjectLockConfiguration]_ - Default retention configuration. |

__Example__
```java
ObjectLockConfiguration config =
    obstorClient.getObjectLockConfiguration(
	    GetObjectLockConfigurationArgs.builder().bucket("my-bucketname").build());
System.out.println("Mode: " + config.mode());
System.out.println("Duration: " + config.duration().duration() + " " + config.duration().unit());
```

<a name="listBuckets"></a>
### listBuckets()
`public List<Bucket> listBuckets()` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#listBuckets--)_

Lists bucket information of all buckets.

| Returns                                        |
|:-----------------------------------------------|
| _List<[Bucket]>_ - List of bucket information. |

__Example__
```java
List<Bucket> bucketList = obstorClient.listBuckets();
for (Bucket bucket : bucketList) {
  System.out.println(bucket.creationDate() + ", " + bucket.name());
}
```

<a name="listBuckets"></a>
### listBuckets(ListBucketsArgs args)
`public List<Bucket> listBuckets(ListBucketsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#listBuckets-net.obstor.ListBucketsArgs-)_

Lists bucket information of all buckets.

__Parameters__
| Parameter | Type                | Description |
|:----------|:--------------------|:------------|
| ``args``  | _[ListBucketsArgs]_ | Arguments.  |

| Returns                                        |
|:-----------------------------------------------|
| _List<[Bucket]>_ - List of bucket information. |

__Example__
```java
List<Bucket> bucketList =
    obstorClient.listBuckets(ListBuckets.builder().extraHeaders(headers).build());
for (Bucket bucket : bucketList) {
  System.out.println(bucket.creationDate() + ", " + bucket.name());
}
```

<a name="listenBucketNotification"></a>
### listenBucketNotification(ListenBucketNotificationArgs args)
`public CloseableIterator<Result<NotificationRecords>> listenBucketNotification(ListenBucketNotificationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#listenBucketNotification-net.obstor.ListenBucketNotificationArgs-)_

Listens events of object prefix and suffix of a bucket. The returned closable iterator is lazily evaluated hence its required to iterate to get new records and must be used with try-with-resource to release underneath network resources.

__Parameters__
| Parameter | Type                             | Description |
|:----------|:---------------------------------|:------------|
| ``args``  | _[ListenBucketNotificationArgs]_ | Arguments.  |

| Returns                                                                                                 |
|:--------------------------------------------------------------------------------------------------------|
| _[CloseableIterator]<[Result]<[NotificationRecords]>>_ - Lazy closable iterator contains event records. |

__Example__
```java
String[] events = {"s3:ObjectCreated:*", "s3:ObjectAccessed:*"};
try (CloseableIterator<Result<NotificationRecords>> ci =
    obstorClient.listenBucketNotification(
        ListenBucketNotificationArgs.builder()
            .bucket("bucketName")
            .prefix("")
            .suffix("")
            .events(events)
            .build())) {
  while (ci.hasNext()) {
    NotificationRecords records = ci.next().get();
    for (Event event : records.events()) {
      System.out.println("Event " + event.eventType() + " occurred at " + event.eventTime()
          + " for " + event.bucketName() + "/" + event.objectName());
    }
  }
}
```

<a name="listObjects"></a>
### listObjects(ListObjectsArgs args)
`public Iterable<Result<Item>> listObjects(ListObjectsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#listObjects-net.obstor.ListObjectsArgs-)_

Lists object information of a bucket.

__Parameters__
| Parameter        | Type                | Description               |
|:-----------------|:--------------------|:--------------------------|
| ``args``         | _[ListObjectsArgs]_ | Arguments to list objects |

| Returns                                                                   |
|:--------------------------------------------------------------------------|
| _Iterable<[Result]<[Item]>>_ - Lazy iterator contains object information. |

__Example__
```java
// Lists objects information.
Iterable<Result<Item>> results = obstorClient.listObjects(
    ListObjectsArgs.builder().bucket("my-bucketname").build());

// Lists objects information recursively.
Iterable<Result<Item>> results = obstorClient.listObjects(
    ListObjectsArgs.builder().bucket("my-bucketname").recursive(true).build());

// Lists maximum 100 objects information whose names starts with 'E' and after 'ExampleGuide.pdf'.
Iterable<Result<Item>> results = obstorClient.listObjects(
    ListObjectsArgs.builder()
        .bucket("my-bucketname")
        .startAfter("ExampleGuide.pdf")
        .prefix("E")
        .maxKeys(100)
        .build());

// Lists maximum 100 objects information with version whose names starts with 'E' and after
// 'ExampleGuide.pdf'.
Iterable<Result<Item>> results = obstorClient.listObjects(
    ListObjectsArgs.builder()
        .bucket("my-bucketname")
        .startAfter("ExampleGuide.pdf")
        .prefix("E")
        .maxKeys(100)
        .includeVersions(true)
        .build());
```

### makeBucket(MakeBucketArgs args)
`public void makeBucket(MakeBucketArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#makeBucket-net.obstor.MakeBucketArgs-)_

Creates a bucket with given region and object lock feature enabled.

__Parameters__

| Parameter      | Type               | Description                |
|:---------------|:-------------------|:---------------------------|
| ``args``       | _[MakeBucketArgs]_ | Arguments to create bucket |

__Example__

```java
// Create bucket with default region.
obstorClient.makeBucket(
    MakeBucketArgs.builder()
        .bucket("my-bucketname")
        .build());

// Create bucket with specific region.
obstorClient.makeBucket(
    MakeBucketArgs.builder()
        .bucket("my-bucketname")
        .region("us-west-1")
        .build());

// Create object-lock enabled bucket with specific region.
obstorClient.makeBucket(
    MakeBucketArgs.builder()
        .bucket("my-bucketname")
        .region("us-west-1")
        .objectLock(true)
        .build());
```

<a name="removeBucket"></a>
### removeBucket(RemoveBucketArgs args)
`public void removeBucket(RemoveBucketArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#removeBucket-net.obstor.RemoveBucketArgs-)_

Removes an empty bucket.

__Parameters__

| Parameter    | Type                 | Description     |
|:-------------|:---------------------|:----------------|
| ``args``     | _[RemoveBucketArgs]_ | Arguments.      |

__Example__
```java
obstorClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
```

<a name="setBucketCors"></a>
### setBucketCors(SetBucketCorsArgs args)
`public void setBucketCors(SetBucketCorsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketCors-net.obstor.SetBucketCorsArgs-)_

Sets CORS configuration to a bucket.

__Parameters__

| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[SetBucketCorsArgs]_ | Arguments.  |

__Example__
```java
CORSConfiguration config =
    new CORSConfiguration(
        Arrays.asList(
            new CORSConfiguration.CORSRule[] {
              // Rule 1
              new CORSConfiguration.CORSRule(
                  Arrays.asList(new String[] {"*"}), // Allowed headers
                  Arrays.asList(new String[] {"PUT", "POST", "DELETE"}), // Allowed methods
                  Arrays.asList(new String[] {"http://www.example.com"}), // Allowed origins
                  Arrays.asList(
                      new String[] {"x-amz-server-side-encryption"}), // Expose headers
                  null, // ID
                  3000), // Maximum age seconds
              // Rule 2
              new CORSConfiguration.CORSRule(
                  null, // Allowed headers
                  Arrays.asList(new String[] {"GET"}), // Allowed methods
                  Arrays.asList(new String[] {"*"}), // Allowed origins
                  null, // Expose headers
                  null, // ID
                  null // Maximum age seconds
                  )
            }));
obstorClient.setBucketCors(SetBucketCorsArgs.builder().bucket("my-bucketname").config(config).build());
```

<a name="setBucketEncryption"></a>
### setBucketEncryption(SetBucketEncryptionArgs args)
`public void setBucketEncryption(SetBucketEncryptionArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketEncryption-net.obstor.SetBucketEncryptionArgs-)_

Sets encryption configuration of a bucket.

__Parameters__
| Parameter | Type                        | Description |
|:----------|:----------------------------|:------------|
| ``args``  | _[SetBucketEncryptionArgs]_ | Arguments.  |

__Example__
```java
obstorClient.setBucketEncryption(
    SetBucketEncryptionArgs.builder().bucket("my-bucketname").config(config).build());
 ```

<a name="setBucketLifecycle"></a>
### setBucketLifecycle(SetBucketLifecycleArgs args)
`public void setBucketLifecycle(SetBucketLifecycleArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketLifecycle-net.obstor.SetBucketLifecycleArgs-)_

Sets lifecycle configuration to a bucket.

__Parameters__
| Parameter | Type                       | Description |
|:----------|:---------------------------|:------------|
| ``args``  | _[SetBucketLifecycleArgs]_ | Arguments.  |

__Example__
```java
List<LifecycleRule> rules = new LinkedList<>();
rules.add(
    new LifecycleRule(
        Status.ENABLED,
        null,
        null,
        new RuleFilter("documents/"),
        "rule1",
        null,
        null,
        new Transition((ZonedDateTime) null, 30, "GLACIER")));
rules.add(
    new LifecycleRule(
        Status.ENABLED,
        null,
        new Expiration((ZonedDateTime) null, 365, null),
        new RuleFilter("logs/"),
        "rule2",
        null,
        null,
        null));
LifecycleConfiguration config = new LifecycleConfiguration(rules);
obstorClient.setBucketLifecycle(
    SetBucketLifecycleArgs.builder().bucket("my-bucketname").config(config).build());
```

<a name="setBucketNotification"></a>
### setBucketNotification(SetBucketNotificationArgs args)
`public void setBucketNotification(SetBucketNotificationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketNotification-net.obstor.SetBucketNotificationArgs-)_

Sets notification configuration to a bucket.

__Parameters__

| Parameter | Type                          | Description |
|:----------|:------------------------------|:------------|
| ``args``  | _[SetBucketNotificationArgs]_ | Arguments.  |

__Example__
```java
List<EventType> eventList = new LinkedList<>();
eventList.add(EventType.OBJECT_CREATED_PUT);
eventList.add(EventType.OBJECT_CREATED_COPY);

QueueConfiguration queueConfiguration = new QueueConfiguration();
queueConfiguration.setQueue("arn:obstor:sqs::1:webhook");
queueConfiguration.setEvents(eventList);
queueConfiguration.setPrefixRule("images");
queueConfiguration.setSuffixRule("pg");

List<QueueConfiguration> queueConfigurationList = new LinkedList<>();
queueConfigurationList.add(queueConfiguration);

NotificationConfiguration config = new NotificationConfiguration();
config.setQueueConfigurationList(queueConfigurationList);

obstorClient.setBucketNotification(
    SetBucketNotificationArgs.builder().bucket("my-bucketname").config(config).build());
```

<a name="setBucketPolicy"></a>
### setBucketPolicy(SetBucketPolicyArgs args)
`public void setBucketPolicy(SetBucketPolicyArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketPolicy-net.obstor.SetBucketPolicyArgs-)_

Sets bucket policy configuration to a bucket.

__Parameters__

| Parameter | Type                    | Description |
|:----------|:------------------------|:------------|
| ``args``  | _[SetBucketPolicyArgs]_ | Arguments.  |

__Example__
```java
// Assume policyJson contains below JSON string;
// {
//     "Statement": [
//         {
//             "Action": [
//                 "s3:GetBucketLocation",
//                 "s3:ListBucket"
//             ],
//             "Effect": "Allow",
//             "Principal": "*",
//             "Resource": "arn:aws:s3:::my-bucketname"
//         },
//         {
//             "Action": "s3:GetObject",
//             "Effect": "Allow",
//             "Principal": "*",
//             "Resource": "arn:aws:s3:::my-bucketname/myobject*"
//         }
//     ],
//     "Version": "2012-10-17"
// }
//
obstorClient.setBucketPolicy(
    SetBucketPolicyArgs.builder().bucket("my-bucketname").config(policyJson).build());
```

<a name="setBucketReplication"></a>
### setBucketReplication(SetBucketReplicationArgs args)
`public void setBucketReplication(SetBucketReplicationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketReplication-net.obstor.SetBucketReplicationArgs-)_

Sets bucket replication configuration to a bucket.

__Parameters__

| Parameter | Type                         | Description |
|:----------|:-----------------------------|:------------|
| ``args``  | _[SetBucketReplicationArgs]_ | Arguments.  |

__Example__
```java
Map<String, String> tags = new HashMap<>();
tags.put("key1", "value1");
tags.put("key2", "value2");

ReplicationRule rule =
    new ReplicationRule(
        new DeleteMarkerReplication(Status.DISABLED),
        new ReplicationDestination(
            null, null, "REPLACE-WITH-ACTUAL-DESTINATION-BUCKET-ARN", null, null, null, null),
        null,
        new RuleFilter(new AndOperator("TaxDocs", tags)),
        "rule1",
        null,
        1,
        null,
        Status.ENABLED);

List<ReplicationRule> rules = new LinkedList<>();
rules.add(rule);

ReplicationConfiguration config =
    new ReplicationConfiguration("REPLACE-WITH-ACTUAL-ROLE", rules);

obstorClient.setBucketReplication(
    SetBucketReplicationArgs.builder().bucket("my-bucketname").config(config).build());
```

<a name="setBucketTags"></a>
### setBucketTags(SetBucketTagsArgs args)
`public void setBucketTags(SetBucketTagsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketTags-net.obstor.SetBucketTagsArgs-)_

Sets tags to a bucket.

__Parameters__

| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[SetBucketTagsArgs]_ | Arguments.  |

__Example__
```java
Map<String, String> map = new HashMap<>();
map.put("Project", "Project One");
map.put("User", "jsmith");
obstorClient.setBucketTags(SetBucketTagsArgs.builder().bucket("my-bucketname").tags(map).build());
```

<a name="setBucketVersioning"></a>
### setBucketVersioning(SetBucketVersioningArgs args)
`public void setBucketVersioning(SetBucketVersioningArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setBucketVersioning-net.obstor.SetBucketVersioningArgs-)_

Sets versioning configuration of a bucket.

__Parameters__
| Parameter | Type                        | Description |
|:----------|:----------------------------|:------------|
| ``args``  | _[SetBucketVersioningArgs]_ | Arguments.  |

__Example__
```java
obstorClient.setBucketVersioning(
    SetBucketVersioningArgs.builder().bucket("my-bucketname").config(config).build());
 ```

<a name="setObjectLockConfiguration"></a>
### setObjectLockConfiguration(SetObjectLockConfigurationArgs args)
`public void setObjectLockConfiguration(SetObjectLockConfigurationArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setObjectLockConfiguration-net.obstor.SetObjectLockConfigurationArgs-)_

Sets object-lock configuration in a bucket.

__Parameters__
| Parameter | Type                               | Description |
|:----------|:-----------------------------------|:------------|
| ``args``  | _[SetObjectLockConfigurationArgs]_ | Arguments.  |

__Example__
```java
ObjectLockConfiguration config =
    new ObjectLockConfiguration(RetentionMode.COMPLIANCE, new RetentionDurationDays(100));
obstorClient.setObjectLockConfiguration(
    SetObjectLockConfigurationArgs.builder().bucket("my-bucketname").config(config).build());
```

## 3. Object operations

<a name="composeObject"></a>
### composeObject(ComposeObjectArgs args)
`public ObjectWriteResponse composeObject(ComposeObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#composeObject-net.obstor.ComposeObjectArgs--)_

Creates an object by combining data from different source objects using server-side copy.

 __Parameters__
| Param          | Type                     | Description   |
|:---------------|:-------------------------|:--------------|
| ``args``       | _[ComposeObjectArgs]_    | Arguments.    |

| Returns                                                          |
|:-----------------------------------------------------------------|
| _[ObjectWriteResponse]_ - Contains information of created object.|

__Example__
 ```java
List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
sourceObjectList.add(
  ComposeSource.builder().bucket("my-job-bucket").object("my-objectname-part-one").build());
sourceObjectList.add(
  ComposeSource.builder().bucket("my-job-bucket").object("my-objectname-part-two").build());
sourceObjectList.add(
  ComposeSource.builder().bucket("my-job-bucket").object("my-objectname-part-three").build());

// Create my-bucketname/my-objectname by combining source object list.
obstorClient.composeObject(
  ComposeObjectArgs.builder()
      .bucket("my-bucketname")
      .object("my-objectname")
      .sources(sourceObjectList)
      .build());

// Create my-bucketname/my-objectname with user metadata by combining source object
// list.
Map<String, String> userMetadata = new HashMap<>();
userMetadata.put("My-Project", "Project One");
obstorClient.composeObject(
    ComposeObjectArgs.builder()
      .bucket("my-bucketname")
      .object("my-objectname")
      .sources(sourceObjectList)
      .userMetadata(userMetadata)
      .build());

// Create my-bucketname/my-objectname with user metadata and server-side encryption
// by combining source object list.
obstorClient.composeObject(
  ComposeObjectArgs.builder()
      .bucket("my-bucketname")
      .object("my-objectname")
      .sources(sourceObjectList)
      .userMetadata(userMetadata)
      .ssec(sse)
      .build());
```

<a name="copyObject"></a>
### copyObject(CopyObjectArgs args)
`public ObjectWriteResponse copyObject(CopyObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#copyObject-net.obstor.CopyObjectArgs-)_

Creates an object by server-side copying data from another object.

__Parameters__
| Parameter | Type               | Description |
|:----------|:-------------------|:------------|
| ``args``  | _[CopyObjectArgs]_ | Arguments.  |

| Returns                                                          |
|:-----------------------------------------------------------------|
| _[ObjectWriteResponse]_ - Contains information of created object.|

__Example__

```java
// Create object "my-objectname" in bucket "my-bucketname" by copying from object
// "my-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-objectname")
                .build())
        .build());

// Create object "my-objectname" in bucket "my-bucketname" by copying from object
// "my-source-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-source-objectname")
                .build())
        .build());

// Create object "my-objectname" in bucket "my-bucketname" with SSE-KMS server-side
// encryption by copying from object "my-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-objectname")
                .build())
        .sse(sseKms) // Replace with actual key.
        .build());

// Create object "my-objectname" in bucket "my-bucketname" with SSE-S3 server-side
// encryption by copying from object "my-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-objectname")
                .build())
        .sse(sseS3) // Replace with actual key.
        .build());

// Create object "my-objectname" in bucket "my-bucketname" with SSE-C server-side encryption
// by copying from object "my-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-objectname")
                .build())
        .sse(ssec) // Replace with actual key.
        .build());

// Create object "my-objectname" in bucket "my-bucketname" by copying from SSE-C encrypted
// object "my-source-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-source-objectname")
                .ssec(ssec) // Replace with actual key.
                .build())
        .build());

// Create object "my-objectname" in bucket "my-bucketname" with custom headers conditionally
// by copying from object "my-objectname" in bucket "my-source-bucketname".
obstorClient.copyObject(
    CopyObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .source(
            CopySource.builder()
                .bucket("my-source-bucketname")
                .object("my-objectname")
                .matchETag(etag) // Replace with actual etag.
                .build())
        .headers(headers) // Replace with actual headers.
        .build());
```

<a name="deleteObjectTags"></a>
### deleteObjectTags(DeleteObjectTagsArgs args)
`private void deleteObjectTags(DeleteObjectTagsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#deleteObjectTags-net.obstor.DeleteObjectTagsArgs-)_

Deletes tags of an object.

__Parameters__
| Parameter | Type                     | Description |
|:----------|:-------------------------|:------------|
| ``args``  | _[DeleteObjectTagsArgs]_ | Arguments.  |

__Example__
```java
obstorClient.deleteObjectTags(
    DeleteObjectArgs.builder().bucket("my-bucketname").object("my-objectname").build());
```

<a name="disableObjectLegalHold"></a>
### disableObjectLegalHold(DisableObjectLegalHoldArgs args)
`public void disableObjectLegalHold(DisableObjectLegalHoldArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#disableObjectLegalHold-net.obstor.DisableObjectLegalHoldArgs-)_

Disables legal hold on an object.

 __Parameters__

| Parameter      | Type                           | Description  |
|:---------------|:-------------------------------|:-------------|
| ``args``       | _[DisableObjectLegalHoldArgs]_ | Arguments.   |

 __Example__

```java
// Disables legal hold on an object.
obstorClient.disableObjectLegalHold(
    DisableObjectLegalHoldArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .build());
```

<a name="enableObjectLegalHold"></a>
### enableObjectLegalHold(EnableObjectLegalHoldArgs args)
`public void enableObjectLegalHold(EnableObjectLegalHoldArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#enableObjectLegalHold-net.obstor.EnableObjectLegalHoldArgs-)_

Enables legal hold on an object.

 __Parameters__

| Parameter | Type                          | Description |
|:----------|:------------------------------|:------------|
| ``args``  | _[EnableObjectLegalHoldArgs]_ | Arguments.  |


 __Example__
 ```java

 // Disables legal hold on an object.
obstorClient.enableObjectLegalHold(
    EnableObjectLegalHoldArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .build());
```

<a name="getObject"></a>
### getObject(GetObjectArgs args)
`public InputStream getObject(GetObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObject-net.obstor.GetObjectArgs-)_

Gets data of an object. Returned `InputStream` must be closed after use to release network resources.

__Parameters__
| Parameter      | Type            | Description                |
|:---------------|:----------------|:---------------------------|
| ``args``       | _GetObjectArgs_ | Arguments.                 |

| Returns                               |
|:--------------------------------------|
| _InputStream_ - Contains object data. |

__Example__
```java
// get object given the bucket and object name
try (InputStream stream = obstorClient.getObject(
  GetObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .build())) {
  // Read data from stream
}

// get object data from offset
try (InputStream stream = obstorClient.getObject(
  GetObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .offset(1024L)
  .build())) {
  // Read data from stream
}

// get object data from offset to length
try (InputStream stream = obstorClient.getObject(
  GetObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .offset(1024L)
  .length(4096L)
  .build())) {
  // Read data from stream
}

// get data of an SSE-C encrypted object
try (InputStream stream = obstorClient.getObject(
  GetObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .ssec(ssec)
  .build())) {
  // Read data from stream
}

// get object data from offset to length of an SSE-C encrypted object
try (InputStream stream = obstorClient.getObject(
  GetObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .offset(1024L)
  .length(4096L)
  .ssec(ssec)
  .build())) {
  // Read data from stream
}
```

<a name="getObjectAcl"></a>
### getObjectAcl(GetObjectAclArgs args)
`public Acl getObjectAcl(GetObjectAclArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObjectAcl-net.obstor.GetObjectAclArgs-)_

Gets tags of an object.

__Parameters__
| Parameter | Type                 | Description |
|:----------|:---------------------|:------------|
| ``args``  | _[GetObjectAclArgs]_ | Arguments.  |


| Returns                                          |
|:-------------------------------------------------|
| _[AccessControlPolicy]_ - Access control policy. |

__Example__
```java
AccessControlPolicy policy = obstorClient.getObjectAcl(
    GetObjectAclArgs.builder().bucket("my-bucketname").object("my-objectname").build());
```

<a name="getObjectAttributes"></a>
### getObjectAttributes(GetObjectAttributesArgs args)
`public GetObjectAttributesResponse getObjectAttributes(GetObjectAttributesArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObjectAttributes-net.obstor.GetObjectAttributesArgs-)_

Gets tags of an object.

__Parameters__
| Parameter | Type                        | Description |
|:----------|:----------------------------|:------------|
| ``args``  | _[GetObjectAttributesArgs]_ | Arguments.  |


| Returns                                     |
|:--------------------------------------------|
| _[GetObjectAttributesResponse]_ - Response. |

__Example__
```java
GetObjectAttributesResponse response =
    obstorClient.getObjectAttributes(
        GetObjectAttributesArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectname")
            .objectAttributes(
                new String[] {
                  "ETag", "Checksum", "ObjectParts", "StorageClass", "ObjectSize"
                })
            .build());
```

<a name="downloadObject"></a>
### downloadObject(DownloadObjectArgs args)
`public void downloadObject(DownloadObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObject-net.obstor.DownloadObjectArgs-)_

Downloads data of an object to file.

__Parameters__
| Parameter        | Type                 | Description                  |
|:-----------------|:---------------------|:-----------------------------|
| ``args``         | _DownloadObjectArgs_ | Arguments.                   |

__Example__
```java
// Download object given the bucket, object name and output file name
obstorClient.downloadObject(
  DownloadObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .filename("my-object-file")
  .build());

// Download server-side encrypted object in bucket to given file name
obstorClient.downloadObject(
  DownloadObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .ssec(ssec)
  .filename("my-object-file")
  .build());
```

 <a name="getObjectRetention"></a>
### getObjectRetention(GetObjectRetentionArgs args)
`public Retention getObjectRetention(GetObjectRetentionArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObjectRetention-net.obstor.GetObjectRetentionArgs-)_

Gets retention configuration of an object.

 __Parameters__

| Parameter      | Type                       | Description   |
|:---------------|:---------------------------|:--------------|
| ``args``       | _[GetObjectRetentionArgs]_ | Arguments.    |

| Returns                                         |
|:------------------------------------------------|
| _[Retention]_ - Object retention configuration. |

 __Example__
 ```java
// Object with version id.
Retention retention =
    obstorClient.getObjectRetention(
        GetObjectRetentionArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectname")
            .versionId("object-version-id")
            .build());
System.out.println("mode: " + retention.mode() + "until: " + retention.retainUntilDate());
```

<a name="getObjectTags"></a>
### getObjectTags(GetObjectTagsArgs args)
`public Tags getObjectTags(GetObjectTagsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getObjectTags-net.obstor.GetObjectTagsArgs-)_

Gets tags of an object.

__Parameters__
| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[GetObjectTagsArgs]_ | Arguments.  |


| Returns          |
|:-----------------|
| _[Tags]_ - tags. |

__Example__
```java
Tags tags = obstorClient.getObjectTags(
    GetObjectTagsArgs.builder().bucket("my-bucketname").object("my-objectname").build());
```

 <a name="getPresignedObjectUrl"></a>
### getPresignedObjectUrl(GetPresignedObjectUrlArgs args)
`public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getPresignedObjectUrl-net.obstor.GetPresignedObjectUrlArgs-)_

Gets presigned URL of an object for HTTP method, expiry time and custom request parameters.

 __Parameters__
| Parameter   | Type                           | Description  |
|:------------|:-------------------------------|:-------------|
| ``args``    | _[GetPresignedObjectUrlArgs]_  | Arguments.   |

| Returns                |
|:-----------------------|
| _String_ - URL string. |

 __Example__
 ```java
// Get presigned URL string to download 'my-objectname' in 'my-bucketname'
// with an expiration of 2 hours.
//
// Additionally also add 'response-content-type' to dynamically set content-type
// for the server response.
Map<String, String> reqParams = new HashMap<String, String>();
reqParams.put("response-content-type", "application/json");

String url =
    obstorClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket("my-bucketname")
            .object("my-objectname")
            .expiry(2, TimeUnit.HOURS)
            .extraQueryParams(reqParams)
            .build());
System.out.println(url);

// Get presigned URL string to upload 'my-objectname' in 'my-bucketname'
// with an expiration of 1 day.
String url =
    obstorClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.PUT)
            .bucket("my-bucketname")
            .object("my-objectname")
            .expiry(1, TimeUnit.DAYS)
            .build());
System.out.println(url);

// Get presigned URL string to lookup metadata for 'my-objectname' in 'my-bucketname'
// with an expiration of 2 hours.
//
// Additionally also add 'response-content-type' to dynamically set content-type
// for the server metadata response.
Map<String, String> reqParams = new HashMap<String, String>();
reqParams.put("response-content-type", "application/json");

String url =
    obstorClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.HEAD)
            .bucket("my-bucketname")
            .object("my-objectname")
            .expiry(2, TimeUnit.HOURS)
            .extraQueryParams(reqParams)
            .build());
System.out.println(url);
```

 <a name="isObjectLegalHoldEnabled"></a>
### isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args)
`public boolean isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#isObjectLegalHoldEnabled-net.obstor.IsObjectLegalHoldEnabledArgs-)_

Returns true if legal hold is enabled on an object.

 __Parameters__

| Parameter | Type                             | Description  |
|:----------|:---------------------------------|:-------------|
| ``args``  | _[IsObjectLegalHoldEnabledArgs]_ | Arguments.   |


| Returns                                    |
|:-------------------------------------------|
| _boolean_ - True if legal hold is enabled. |

 __Example__

```java
boolean status =
    s3Client.isObjectLegalHoldEnabled(
       IsObjectLegalHoldEnabledArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectname")
            .versionId("object-versionId")
            .build());
if (status) {
  System.out.println("Legal hold is on");
else {
  System.out.println("Legal hold is off");
}
```

<a name="getPresignedPostFormData"></a>
### getPresignedPostFormData(PostPolicy policy)
`public Map<String,String> getPresignedPostFormData(PostPolicy policy)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#getPresignedPostFormData-net.obstor.PostPolicy-)_

Gets form-data of [PostPolicy] of an object to upload its data using POST method.

__Parameters__
| Parameter  | Type           | Description               |
|:-----------|:---------------|:--------------------------|
| ``policy`` | _[PostPolicy]_ | Post policy of an object. |

| Returns                                                                           |
|:----------------------------------------------------------------------------------|
| _Map<String, String>_ - Contains form-data to upload an object using POST method. |

__Example__
```java
// Create new post policy for 'my-bucketname' with 7 days expiry from now.
PostPolicy policy = new PostPolicy("my-bucketname", ZonedDateTime.now().plusDays(7));

// Add condition that 'key' (object name) equals to 'my-objectname'.
policy.addEqualsCondition("key", "my-objectname");

// Add condition that 'Content-Type' starts with 'image/'.
policy.addStartsWithCondition("Content-Type", "image/");

// Add condition that 'content-length-range' is between 64kiB to 10MiB.
policy.addContentLengthRangeCondition(64 * 1024, 10 * 1024 * 1024);

Map<String, String> formData = obstorClient.getPresignedPostFormData(policy);

// Upload an image using POST object with form-data.
MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
multipartBuilder.setType(MultipartBody.FORM);
for (Map.Entry<String, String> entry : formData.entrySet()) {
  multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
}
multipartBuilder.addFormDataPart("key", "my-objectname");
multipartBuilder.addFormDataPart("Content-Type", "image/png");

// "file" must be added at last.
multipartBuilder.addFormDataPart(
    "file", "my-objectname", RequestBody.create(new File("Pictures/avatar.png"), null));

Request request =
    new Request.Builder()
        .url("https://demo.obstor.net/my-bucketname")
        .post(multipartBuilder.build())
        .build();
OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
Response response = httpClient.newCall(request).execute();
if (response.isSuccessful()) {
  System.out.println("Pictures/avatar.png is uploaded successfully using POST object");
} else {
  System.out.println("Failed to upload Pictures/avatar.png");
}
```

<a name="promptObject"></a>
### promptObject(PromptObjectArgs args)
`public ObjectWriteResponse promptObject(PromptObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#promptObject-net.obstor.PromptObjectArgs-)_

Performs language model inference with the prompt and referenced object as context.

__Parameters__
| Parameter | Type                 | Description |
|:----------|:---------------------|:------------|
| ``args``  | _[PromptObjectArgs]_ | Arguments.  |

| Returns                              |
|:-------------------------------------|
| _[PromptObjectResponse]_ - response. |

<a name="putObject"></a>
### putObject(PutObjectArgs args)
`public ObjectWriteResponse putObject(PutObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#putObject-net.obstor.PutObjectArgs-)_

Uploads given stream as object in bucket.

__Parameters__
| Parameter | Type              | Description |
|:----------|:------------------|:------------|
| ``args``  | _[PutObjectArgs]_ | Arguments.  |

| Returns                                                          |
|:-----------------------------------------------------------------|
| _[ObjectWriteResponse]_ - Contains information of created object.|

__Example__
```java
// Upload known sized input stream.
obstorClient.putObject(
    PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
            inputStream, size, -1)
        .contentType("video/mp4")
        .build());

// Upload unknown sized input stream.
obstorClient.putObject(
    PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
            inputStream, -1, 10485760)
        .contentType("video/mp4")
        .build());

// Create object ends with '/' (also called as folder or directory).
obstorClient.putObject(
    PutObjectArgs.builder().bucket("my-bucketname").object("path/to/").stream(
            new ByteArrayInputStream(new byte[] {}), 0, -1)
        .build());

// Upload input stream with headers and user metadata.
Map<String, String> headers = new HashMap<>();
headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
Map<String, String> userMetadata = new HashMap<>();
userMetadata.put("My-Project", "Project One");
obstorClient.putObject(
    PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
            inputStream, size, -1)
        .headers(headers)
        .userMetadata(userMetadata)
        .build());

// Upload input stream with server-side encryption.
obstorClient.putObject(
    PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
            inputStream, size, -1)
        .sse(sse)
        .build());
```

<a name="putObjectFanOut"></a>
### putObjectFanOut(PutObjectFanOutArgs args)
`public PutObjectFanOutResponse putObjectFanOut(PutObjectFanOutArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#putObjectFanOut-net.obstor.PutObjectFanOutArgs-)_

Uploads multiple objects with same content from single stream with optional metadata and tags.

__Parameters__
| Parameter | Type                    | Description |
|:----------|:------------------------|:------------|
| ``args``  | _[PutObjectFanOutArgs]_ | Arguments.  |


| Returns                                 |
|:----------------------------------------|
| _[PutObjectFanOutResponse]_ - response. |

__Example__
```java
Map<String, String> map = new HashMap<>();
map.put("Project", "Project One");
map.put("User", "jsmith");
PutObjectFanOutResponse response =
    obstorClient.putObjectFanOut(
        PutObjectFanOutArgs.builder().bucket("my-bucketname").stream(
                new ByteArrayInputStream("somedata".getBytes(StandardCharsets.UTF_8)), 8)
            .entries(
                Arrays.asList(
                    new PutObjectFanOutEntry[] {
                      PutObjectFanOutEntry.builder().key("fan-out.0").build(),
                      PutObjectFanOutEntry.builder().key("fan-out.1").tags(map).build()
                    }))
            .build());
```

<a name="uploadObject"></a>
### uploadObject(UploadObjectArgs args)
`public void uploadObject(UploadObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#uploadObject-net.obstor.UploadObjectArgs-)_

Uploads contents from a file as object in bucket.

__Parameters__
| Parameter | Type                 | Description |
|:----------|:---------------------|:------------|
| ``args``  | _[UploadObjectArgs]_ | Arguments.  |

__Example__
```java
// Upload an JSON file.
obstorClient.uploadObject(
    UploadObjectArgs.builder()
        .bucket("my-bucketname").object("my-objectname").filename("person.json").build());

// Upload a video file.
obstorClient.uploadObject(
    UploadObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .filename("my-video.avi")
        .contentType("video/mp4")
        .build());
```

<a name="uploadSnowballObjects"></a>
### uploadSnowballObjects(UploadSnowballObjectsArgs args)
`public void uploadSnowballObjects(UploadSnowballObjectsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#uploadSnowballObjects-net.obstor.UploadSnowballObjectsArgs-)_

Uploads multiple objects in a single put call. It is done by creating intermediate TAR file optionally compressed which is uploaded to S3 service.

__Parameters__
| Parameter | Type                          | Description |
|:----------|:------------------------------|:------------|
| ``args``  | _[UploadSnowballObjectsArgs]_ | Arguments.  |

__Example__
```java
List<SnowballObject> objects = new ArrayList<SnowballObject>();
objects.add(
    new SnowballObject(
        "my-object-one",
        new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8)),
        5,
        null));
objects.add(
    new SnowballObject(
        "my-object-two",
        new ByteArrayInputStream("java".getBytes(StandardCharsets.UTF_8)),
        4,
        null));
obstorClient.uploadSnowballObjects(
    UploadSnowballObjectsArgs.builder().bucket("my-bucketname").objects(objects).build());
```

<a name="removeObject"></a>
### removeObject(RemoveObjectArgs args)
`public void removeObject(RemoveObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#removeObject-net.obstor.RemoveObjectArgs-)_

Removes an object.

__Parameters__
| Parameter | Type                 | Description |
|:----------|:---------------------|:------------|
| ``args``  | _[RemoveObjectArgs]_ | Arguments.  |

__Example__
```java
// Remove object.
obstorClient.removeObject(
    RemoveObjectArgs.builder().bucket("my-bucketname").object("my-objectname").build());

// Remove versioned object.
obstorClient.removeObject(
    RemoveObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-versioned-objectname")
        .versionId("my-versionid")
        .build());

// Remove versioned object bypassing Governance mode.
obstorClient.removeObject(
    RemoveObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-versioned-objectname")
        .versionId("my-versionid")
        .bypassRetentionMode(true)
        .build());
```

<a name="removeObjects"></a>
### removeObjects(RemoveObjectsArgs args)
`public Iterable<Result<DeleteError>> removeObjects(RemoveObjectsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#removeObjects-net.obstor.RemoveObjectsArgs-)_

Removes multiple objects lazily. Its required to iterate the returned Iterable to perform removal.

__Parameters__
| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[RemoveObjectsArgs]_ | Arguments.  |

| Returns                                                                             |
|:------------------------------------------------------------------------------------|
| _Iterable<[Result]<[DeleteError]>>_ - Lazy iterator contains object removal status. |

__Example__
```java
List<DeleteObject> objects = new LinkedList<>();
objects.add(new DeleteObject("my-objectname1"));
objects.add(new DeleteObject("my-objectname2"));
objects.add(new DeleteObject("my-objectname3"));
Iterable<Result<DeleteError>> results =
    obstorClient.removeObjects(
        RemoveObjectsArgs.builder().bucket("my-bucketname").objects(objects).build());
for (Result<DeleteError> result : results) {
  DeleteError error = result.get();
  System.out.println(
      "Error in deleting object " + error.objectName() + "; " + error.message());
}
```

<a name="restoreObject"></a>
### restoreObject(RestoreObjectArgs args)
`public void restoreObject(RestoreObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#restoreObject-net.obstor.RestoreObjectArgs-)_

Restores an object.

__Parameters__
| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[RestoreObjectArgs]_ | Arguments.  |

__Example__
```java
// Restore object.
obstorClient.restoreObject(
    RestoreObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .request(new RestoreRequest(null, null, null, null, null, null))
        .build());

// Restore versioned object.
obstorClient.restoreObject(
    RestoreObjectArgs.builder()
        .bucket("my-bucketname")
        .object("my-versioned-objectname")
        .versionId("my-versionid")
        .request(new RestoreRequest(null, null, null, null, null, null))
        .build());
```

 <a name="selectObjectContent"></a>
### selectObjectContent(SelectObjectContentArgs args)
`public SelectResponseStream selectObjectContent(SelectObjectContentArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#selectObjectContent-net.obstor.SelectObjectContentArgs-)_

Selects content of a object by SQL expression.

__Parameters__

| Parameter           | Type                                | Description                           |
|:--------------------|:------------------------------------|:--------------------------------------|
| ``args``            | _[SelectObjectContentArgs]_           | Arguments.                            |

| Returns                                                            |
|:-------------------------------------------------------------------|
| _[SelectResponseStream]_ - Contains filtered records and progress. |

__Example__
```java
String sqlExpression = "select * from S3Object";
InputSerialization is = new InputSerialization(null, false, null, null, FileHeaderInfo.USE, null, null, null);
OutputSerialization os = new OutputSerialization(null, null, null, QuoteFields.ASNEEDED, null);
SelectResponseStream stream =
    obstorClient.selectObjectContent(
        SelectObjectContentArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectName")
            .sqlExpression(sqlExpression)
            .inputSerialization(is)
            .outputSerialization(os)
            .requestProgress(true)
            .build());

byte[] buf = new byte[512];
int bytesRead = stream.read(buf, 0, buf.length);
System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));

Stats stats = stream.stats();
System.out.println("bytes scanned: " + stats.bytesScanned());
System.out.println("bytes processed: " + stats.bytesProcessed());
System.out.println("bytes returned: " + stats.bytesReturned());

stream.close();
```

<a name="setObjectRetention"></a>
### setObjectRetention(SetObjectRetentionArgs args)
`public void setObjectLockRetention(SetObjectRetentionArgs)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setObjectRetention-net.obstor.SetObjectRetentionArgs-)_

Sets retention configuration to an object.

 __Parameters__

| Parameter        | Type                       | Description  |
|:-----------------|:---------------------------|:-------------|
| ``args``         | _[SetObjectRetentionArgs]_ | Arguments.   |

 __Example__
```java
Retention retention = new Retention(RetentionMode.COMPLIANCE, ZonedDateTime.now().plusYears(1));
obstorClient.setObjectRetention(
    SetObjectRetentionArgs.builder()
        .bucket("my-bucketname")
        .object("my-objectname")
        .config(retention)
        .bypassGovernanceMode(true)
        .build());
```

<a name="setObjectTags"></a>
### setObjectTags(SetObjectTagsArgs args)
`public void setObjectTags(SetObjectTagsArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#setObjectTags-net.obstor.SetObjectTagsArgs-)_

Sets tags to an object.

__Parameters__

| Parameter | Type                  | Description |
|:----------|:----------------------|:------------|
| ``args``  | _[SetObjectTagsArgs]_ | Arguments.  |

__Example__
```java
Map<String, String> map = new HashMap<>();
map.put("Project", "Project One");
map.put("User", "jsmith");
obstorClient.setObjectTags(
    SetObjectTagsArgs.builder().bucket("my-bucketname").object("my-objectname").tags(map).build());
```

<a name="statObject"></a>
### statObject(StatObjectArgs args)
`public StatObjectResponse statObject(StatObjectArgs args)` _[[Javadoc]](http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#statObject-net.obstor.StatObjectArgs-)_

Gets object information and metadata of an object.

__Parameters__
| Parameter | Type               | Description |
|:----------|:-------------------|:------------|
| ``args``  | _[StatObjectArgs]_ | Arguments.  |

| Returns                                                             |
|:--------------------------------------------------------------------|
| _[StatObjectResponse]_ - Populated object information and metadata. |

__Example__
```java
// Get information of an object.
StatObjectResponse response =
    obstorClient.statObject(
        StatObjectArgs.builder().bucket("my-bucketname").object("my-objectname").build());

// Get information of SSE-C encrypted object.
StatObjectResponse response =
    obstorClient.statObject(
        StatObjectArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectname")
            .ssec(ssec)
            .build());

// Get information of a versioned object.
StatObjectResponse response =
    obstorClient.statObject(
        StatObjectArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectname")
            .versionId("version-id")
            .build());

// Get information of a SSE-C encrypted versioned object.
StatObjectResponse response =
    obstorClient.statObject(
        StatObjectArgs.builder()
            .bucket("my-bucketname")
            .object("my-objectname")
            .versionId("version-id")
            .ssec(ssec)
            .build());
```

## 5. Explore Further
- [Build your own Photo API Service - Full Application Example ](https://github.com/obstor/obstor-java-rest-example)
- [Complete JavaDoc](http://obstor.github.io/obstor-java/)

[constructor-1]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-
[constructor-2]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.net.URL-
[constructor-3]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-okhttp3.HttpUrl-
[constructor-4]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-java.lang.String-java.lang.String-
[constructor-5]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-int-java.lang.String-java.lang.String-
[constructor-6]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-java.lang.String-java.lang.String-boolean-
[constructor-7]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-int-java.lang.String-java.lang.String-java.lang.String-boolean-
[constructor-8]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-okhttp3.HttpUrl-java.lang.String-java.lang.String-
[constructor-9]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.net.URL-java.lang.String-java.lang.String-
[constructor-10]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-java.lang.String-java.lang.String-java.lang.String-
[constructor-11]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-int-java.lang.String-java.lang.String-java.lang.String-boolean-
[constructor-12]: http://obstor.github.io/obstor-java/net/obstor/ObstorClient.html#ObstorClient-java.lang.String-java.lang.Integer-java.lang.String-java.lang.String-java.lang.String-java.lang.Boolean-okhttp3.OkHttpClient-
[NotificationConfiguration]: http://obstor.github.io/obstor-java/net/obstor/messages/NotificationConfiguration.html
[ObjectLockConfiguration]: http://obstor.github.io/obstor-java/net/obstor/messages/ObjectLockConfiguration.html
[Bucket]: http://obstor.github.io/obstor-java/net/obstor/messages/Bucket.html
[CloseableIterator]: http://obstor.github.io/obstor-java/net/obstor/CloseableIterator.html
[Result]: http://obstor.github.io/obstor-java/net/obstor/Result.html
[NotificationRecords]: http://obstor.github.io/obstor-java/net/obstor/messages/NotificationRecords.html
[Upload]: http://obstor.github.io/obstor-java/net/obstor/messages/Upload.html
[Item]: http://obstor.github.io/obstor-java/net/obstor/messages/Item.html
[ComposeSource]: http://obstor.github.io/obstor-java/net/obstor/ComposeSource.html
[ServerSideEncryption]: http://obstor.github.io/obstor-java/net/obstor/ServerSideEncryption.html
[ServerSideEncryptionCustomerKey]: http://obstor.github.io/obstor-java/net/obstor/ServerSideEncryptionCustomerKey.html
[CopyConditions]: http://obstor.github.io/obstor-java/net/obstor/CopyConditions.html
[PostPolicy]: http://obstor.github.io/obstor-java/net/obstor/PostPolicy.html
[PutObjectOptions]: http://obstor.github.io/obstor-java/net/obstor/PutObjectOptions.html
[InputSerialization]: http://obstor.github.io/obstor-java/net/obstor/messages/InputSerialization.html
[OutputSerialization]: http://obstor.github.io/obstor-java/net/obstor/messages/OutputSerialization.html
[Retention]: http://obstor.github.io/obstor-java/net/obstor/messages/Retention.html
[StatObjectResponse]: http://obstor.github.io/obstor-java/net/obstor/StatObjectResponse.html
[DeleteError]: http://obstor.github.io/obstor-java/net/obstor/messages/DeleteError.html
[SelectResponseStream]: http://obstor.github.io/obstor-java/net/obstor/SelectResponseStream.html
[MakeBucketArgs]: http://obstor.github.io/obstor-java/net/obstor/MakeBucketArgs.html
[ListObjectsArgs]: http://obstor.github.io/obstor-java/net/obstor/ListObjectsArgs.html
[RemoveBucketArgs]: http://obstor.github.io/obstor-java/net/obstor/RemoveBucketArgs.html
[SetObjectRetentionArgs]: http://obstor.github.io/obstor-java/net/obstor/SetObjectRetentionArgs.html
[GetObjectRetentionArgs]: http://obstor.github.io/obstor-java/net/obstor/GetObjectRetentionArgs.html
[Method]: http://obstor.github.io/obstor-java/net/obstor/http/Method.html
[StatObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/StatObjectArgs.html
[RemoveObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/RemoveObjectArgs.html
[SseConfiguration]: http://obstor.github.io/obstor-java/net/obstor/messages/SseConfiguration.html
[DeleteBucketEncryptionArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketEncryptionArgs.html
[GetBucketEncryptionArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketEncryptionArgs.html
[SetBucketEncryptionArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketEncryptionArgs.html
[Tags]: http://obstor.github.io/obstor-java/net/obstor/messages/Tags.html
[DeleteBucketTagsArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketTagsArgs.html
[GetBucketTagsArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketTagsArgs.html
[SetBucketTagsArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketTagsArgs.html
[DeleteObjectTagsArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteObjectTagsArgs.html
[GetObjectTagsArgs]: http://obstor.github.io/obstor-java/net/obstor/GetObjectTagsArgs.html
[SetObjectTagsArgs]: http://obstor.github.io/obstor-java/net/obstor/SetObjectTagsArgs.html
[LifecycleConfiguration]: http://obstor.github.io/obstor-java/net/obstor/messages/LifecycleConfiguration.html
[DeleteBucketLifecycleArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketLifecycleArgs.html
[GetBucketLifecycleArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketLifecycleArgs.html
[SetBucketLifecycleArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketLifecycleArgs.html
[GetBucketPolicyArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketPolicyArgs.html
[SetBucketPolicyArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketPolicyArgs.html
[DeleteBucketPolicyArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketPolicyArgs.html
[GetObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/GetObjectArgs.html
[DownloadObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/DownloadObjectArgs.html
[BucketExistsArgs]: http://obstor.github.io/obstor-java/net/obstor/BucketExistsArgs.html
[EnableObjectLegalHoldArgs]: http://obstor.github.io/obstor-java/net/obstor/EnableObjectLegalHoldArgs.html
[DisableObjectLegalHoldArgs]: http://obstor.github.io/obstor-java/net/obstor/DisableObjectLegalHoldArgs.html
[IsObjectLegalHoldEnabledArgs]: http://obstor.github.io/obstor-java/net/obstor/IsObjectLegalHoldEnabledArgs.html
[DeleteBucketNotificationArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketNotificationArgs.html
[GetBucketNotificationArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketNotificationArgs.html
[SetBucketNotificationArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketNotificationArgs.html
[ListenBucketNotificationArgs]: http://obstor.github.io/obstor-java/net/obstor/ListenBucketNotificationArgs.html
[SelectObjectContentArgs]: http://obstor.github.io/obstor-java/net/obstor/SelectObjectContentArgs.html
[GetObjectLockConfigurationArgs]: http://obstor.github.io/obstor-java/net/obstor/GetObjectLockConfigurationArgs.html
[SetObjectLockConfigurationArgs]: http://obstor.github.io/obstor-java/net/obstor/SetObjectLockConfigurationArgs.html
[DeleteObjectLockConfigurationArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteObjectLockConfigurationArgs.html
[GetPresignedObjectUrlArgs]: http://obstor.github.io/obstor-java/net/obstor/GetPresignedObjectUrlArgs.html
[RemoveObjectsArgs]: http://obstor.github.io/obstor-java/net/obstor/RemoveObjectsArgs.html
[CopyObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/CopyObjectArgs.html
[PutObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/PutObjectArgs.html
[UploadObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/UploadObjectArgs.html
[UploadSnowballObjectsArgs]: http://obstor.github.io/obstor-java/net/obstor/UploadSnowballObjectsArgs.html
[ComposeObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/ComposeObjectArgs.html
[ObjectWriteResponse]: http://obstor.github.io/obstor-java/net/obstor/ObjectWriteResponse.html
[ListBucketsArgs]: http://obstor.github.io/obstor-java/net/obstor/ListBucketsArgs.html
[DeleteBucketReplicationArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketReplicationArgs.html
[GetBucketReplicationArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketReplicationArgs.html
[SetBucketReplicationArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketReplicationArgs.html
[ReplicationConfiguration]: http://obstor.github.io/obstor-java/net/obstor/messages/ReplicationConfiguration.html
[VersioningConfiguration]: http://obstor.github.io/obstor-java/net/obstor/messages/VersioningConfiguration.html
[GetBucketVersioningArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketVersioningArgs.html
[SetBucketVersioningArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketVersioningArgs.html
[RestoreObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/RestoreObjectArgs.html
[DeleteBucketCorsArgs]: http://obstor.github.io/obstor-java/net/obstor/DeleteBucketCorsArgs.html
[GetBucketCorsArgs]: http://obstor.github.io/obstor-java/net/obstor/GetBucketCorsArgs.html
[SetBucketCorsArgs]: http://obstor.github.io/obstor-java/net/obstor/SetBucketCorsArgs.html
[GetObjectAclArgs]: http://obstor.github.io/obstor-java/net/obstor/GetObjectAclArgs.html
[AccessControlPolicy]: http://obstor.github.io/obstor-java/net/obstor/messages/AccessControlPolicy.html
[GetObjectAttributesArgs]: http://obstor.github.io/obstor-java/net/obstor/GetObjectAttributesArgs.html
[GetObjectAttributesResponse]: http://obstor.github.io/obstor-java/net/obstor/GetObjectAttributesResponse.html
[PutObjectFanOutArgs]: http://obstor.github.io/obstor-java/net/obstor/PutObjectFanOutArgs.html
[PutObjectFanOutResponse]: http://obstor.github.io/obstor-java/net/obstor/PutObjectFanOutResponse.html
[PromptObjectArgs]: http://obstor.github.io/obstor-java/net/obstor/PromptObjectArgs.html
[PromptObjectResponse]: http://obstor.github.io/obstor-java/net/obstor/PromptObjectResponse.html
