# Spring Boot Full Stack App with Snowplow trackers  
  
This is an experimental version of my app where I have implemented a few trackers, using Snowplow library. 

Please refer to [Snowplow docs](https://docs.snowplowanalytics.com) for full details about thier tracking service.  
  
Snowplow provides an extensive [tracker library](https://docs.snowplowanalytics.com/docs/collecting-data/collecting-from-own-applications/). Since my app is a full stack project I have decided to test the functionality of one of [JavaScript](https://docs.snowplowanalytics.com/docs/collecting-data/collecting-from-own-applications/javascript-trackers/browser-tracker/) trackers on the frontend part of my app and the [Java](https://docs.snowplowanalytics.com/docs/collecting-data/collecting-from-own-applications/java-tracker/) tracker on the backend side.  
  
  

## My implementation  

### 1. Collector  

To collect the data sent from my trackers I used [snowplow-micro](https://github.com/snowplow-incubator/snowplow-micro/), hosted on docker.  


### 2. JavaScript trackers (Browser tracker)  

This is where I tested a couple of out-of-the-box trackers ([trackPageVew](https://docs.snowplowanalytics.com/docs/collecting-data/collecting-from-own-applications/javascript-trackers/browser-tracker/browser-tracker-v3-reference/tracking-events/#page-views), [enableActivityTracking (pagePings)](https://docs.snowplowanalytics.com/docs/collecting-data/collecting-from-own-applications/javascript-trackers/browser-tracker/browser-tracker-v3-reference/tracking-events/#activity-tracking-page-pings)) to make sure that I have connected with snowplow-micro correctly and to inspect collected data, using API provided by the collector.  
  
**Initilizing the trackers**  

Code added to my app.js file:  

```
import {
  newTracker,
  enableActivityTracking,
  trackPageView
} from '@snowplow/browser-tracker';

newTracker('sp', '0.0.0.0:9090', {
  appId: 'fullStackApp-frontend',
});

enableActivityTracking({
  minimumVisitLength: 30,
  heartbeatDelay: 10
});

trackPageView();
```  
   
   
**Received by the collector**  
  

An example of a pageView event:

```
{
    "rawEvent": {
      "api": {
        "vendor": "com.snowplowanalytics.snowplow",
        "version": "tp2"
      },
      "parameters": {
        "e": "pv",
        "duid": "f9bcfa16-8801-4a5e-a5a6-b02a3e2479df",
        "vid": "13",
        "eid": "df073490-6c06-4644-bc7e-ab96bed9532e",
        "url": "http://localhost:3000/",
        "aid": "fullStackApp-frontend",
        "cx": "eyJzY2hlbWEiOiJpZ2x1OmNvbS5zbm93cGxvd2FuYWx5dGljcy5zbm93cGxvdy9jb250ZXh0cy9qc29uc2NoZW1hLzEtMC0wIiwiZGF0YSI6W3sic2NoZW1hIjoiaWdsdTpjb20uc25vd3Bsb3dhbmFseXRpY3Muc25vd3Bsb3cvd2ViX3BhZ2UvanNvbnNjaGVtYS8xLTAtMCIsImRhdGEiOnsiaWQiOiI2YWY5NGQ4NS1iZWUxLTQ4ODMtOThiMi0yYjVhMTEwN2UyMjgifX1dfQ",
        "tna": "sp",
        "cs": "UTF-8",
        "cd": "24",
        "page": "React App",
        "stm": "1621813489717",
        "tv": "js-3.1.0",
        "vp": "1080x1815",
        "ds": "1080x1815",
        "res": "1080x1920",
        "cookie": "1",
        "p": "web",
        "dtm": "1621813489715",
        "lang": "pl-PL",
        "sid": "1bc6a951-2b38-4fa0-b9dc-2f3eae8d9833"
      },
      "contentType": "application/json",
      "source": {
        "name": "ssc-2.2.1-stdout$",
        "encoding": "UTF-8",
        "hostname": "0.0.0.0"
      },
      "context": {
        "timestamp": "2021-05-23T23:44:50.519Z",
        "ipAddress": "172.20.0.1",
        "useragent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
        "refererUri": "http://localhost:3000/",
        "headers": [
          "Timeout-Access: &lt;function1&gt;",
          "Host: 0.0.0.0:9090",
          "Connection: keep-alive",
          "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
          "Accept: */*",
          "Origin: http://localhost:3000",
          "Referer: http://localhost:3000/",
          "Accept-Encoding: gzip, deflate",
          "Accept-Language: pl-PL, pl;q=0.9, en-US;q=0.8, en;q=0.7",
          "application/json"
        ],
        "userId": "ad7f44b0-1acd-4763-99b6-b0c31986198f"
      }
    },
    "eventType": "page_view",
    "schema": "iglu:com.snowplowanalytics.snowplow/page_view/jsonschema/1-0-0",
    "contexts": [
      "iglu:com.snowplowanalytics.snowplow/web_page/jsonschema/1-0-0"
    ],
    "event": {
      "app_id": "fullStackApp-frontend",
      "platform": "web",
      "etl_tstamp": "2021-05-23T23:44:52.437Z",
      "collector_tstamp": "2021-05-23T23:44:50.519Z",
      "dvce_created_tstamp": "2021-05-23T23:44:49.715Z",
      "event": "page_view",
      "event_id": "df073490-6c06-4644-bc7e-ab96bed9532e",
      "txn_id": null,
      "name_tracker": "sp",
      "v_tracker": "js-3.1.0",
      "v_collector": "ssc-2.2.1-stdout$",
      "v_etl": "snowplow-micro-1.1.1-common-1.4.2",
      "user_id": null,
      "user_ipaddress": "172.20.0.1",
      "user_fingerprint": null,
      "domain_userid": "f9bcfa16-8801-4a5e-a5a6-b02a3e2479df",
      "domain_sessionidx": 13,
      "network_userid": "ad7f44b0-1acd-4763-99b6-b0c31986198f",
      "geo_country": null,
      "geo_region": null,
      "geo_city": null,
      "geo_zipcode": null,
      "geo_latitude": null,
      "geo_longitude": null,
      "geo_region_name": null,
      "ip_isp": null,
      "ip_organization": null,
      "ip_domain": null,
      "ip_netspeed": null,
      "page_url": "http://localhost:3000/",
      "page_title": "React App",
      "page_referrer": null,
      "page_urlscheme": "http",
      "page_urlhost": "localhost",
      "page_urlport": 3000,
      "page_urlpath": "/",
      "page_urlquery": null,
      "page_urlfragment": null,
      "refr_urlscheme": null,
      "refr_urlhost": null,
      "refr_urlport": null,
      "refr_urlpath": null,
      "refr_urlquery": null,
      "refr_urlfragment": null,
      "refr_medium": null,
      "refr_source": null,
      "refr_term": null,
      "mkt_medium": null,
      "mkt_source": null,
      "mkt_term": null,
      "mkt_content": null,
      "mkt_campaign": null,
      "contexts": {
        "schema": "iglu:com.snowplowanalytics.snowplow/contexts/jsonschema/1-0-0",
        "data": [
          {
            "schema": "iglu:com.snowplowanalytics.snowplow/web_page/jsonschema/1-0-0",
            "data": {
              "id": "6af94d85-bee1-4883-98b2-2b5a1107e228"
            }
          }
        ]
      },
      "se_category": null,
      "se_action": null,
      "se_label": null,
      "se_property": null,
      "se_value": null,
      "unstruct_event": null,
      "tr_orderid": null,
      "tr_affiliation": null,
      "tr_total": null,
      "tr_tax": null,
      "tr_shipping": null,
      "tr_city": null,
      "tr_state": null,
      "tr_country": null,
      "ti_orderid": null,
      "ti_sku": null,
      "ti_name": null,
      "ti_category": null,
      "ti_price": null,
      "ti_quantity": null,
      "pp_xoffset_min": null,
      "pp_xoffset_max": null,
      "pp_yoffset_min": null,
      "pp_yoffset_max": null,
      "useragent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
      "br_name": null,
      "br_family": null,
      "br_version": null,
      "br_type": null,
      "br_renderengine": null,
      "br_lang": "pl-PL",
      "br_features_pdf": null,
      "br_features_flash": null,
      "br_features_java": null,
      "br_features_director": null,
      "br_features_quicktime": null,
      "br_features_realplayer": null,
      "br_features_windowsmedia": null,
      "br_features_gears": null,
      "br_features_silverlight": null,
      "br_cookies": true,
      "br_colordepth": "24",
      "br_viewwidth": 1080,
      "br_viewheight": 1815,
      "os_name": null,
      "os_family": null,
      "os_manufacturer": null,
      "os_timezone": null,
      "dvce_type": null,
      "dvce_ismobile": null,
      "dvce_screenwidth": 1080,
      "dvce_screenheight": 1920,
      "doc_charset": "UTF-8",
      "doc_width": 1080,
      "doc_height": 1815,
      "tr_currency": null,
      "tr_total_base": null,
      "tr_tax_base": null,
      "tr_shipping_base": null,
      "ti_currency": null,
      "ti_price_base": null,
      "base_currency": null,
      "geo_timezone": null,
      "mkt_clickid": null,
      "mkt_network": null,
      "etl_tags": null,
      "dvce_sent_tstamp": "2021-05-23T23:44:49.717Z",
      "refr_domain_userid": null,
      "refr_dvce_tstamp": null,
      "derived_contexts": {},
      "domain_sessionid": "1bc6a951-2b38-4fa0-b9dc-2f3eae8d9833",
      "derived_tstamp": "2021-05-23T23:44:50.517Z",
      "event_vendor": "com.snowplowanalytics.snowplow",
      "event_name": "page_view",
      "event_format": "jsonschema",
      "event_version": "1-0-0",
      "event_fingerprint": null,
      "true_tstamp": null
    }
  }
```  
  
  
An example of a pagePing event:   
  
```
{
    "rawEvent": {
      "api": {
        "vendor": "com.snowplowanalytics.snowplow",
        "version": "tp2"
      },
      "parameters": {
        "e": "pp",
        "duid": "f9bcfa16-8801-4a5e-a5a6-b02a3e2479df",
        "vid": "13",
        "eid": "259abcd4-f7ee-461e-a11b-8eb93f8d5540",
        "url": "http://localhost:3000/",
        "aid": "fullStackApp-frontend",
        "cx": "eyJzY2hlbWEiOiJpZ2x1OmNvbS5zbm93cGxvd2FuYWx5dGljcy5zbm93cGxvdy9jb250ZXh0cy9qc29uc2NoZW1hLzEtMC0wIiwiZGF0YSI6W3sic2NoZW1hIjoiaWdsdTpjb20uc25vd3Bsb3dhbmFseXRpY3Muc25vd3Bsb3cvd2ViX3BhZ2UvanNvbnNjaGVtYS8xLTAtMCIsImRhdGEiOnsiaWQiOiI2YWY5NGQ4NS1iZWUxLTQ4ODMtOThiMi0yYjVhMTEwN2UyMjgifX1dfQ",
        "tna": "sp",
        "cs": "UTF-8",
        "cd": "24",
        "page": "React App",
        "stm": "1621813519727",
        "tv": "js-3.1.0",
        "vp": "1080x1815",
        "ds": "1400x1800",
        "res": "1080x1920",
        "cookie": "1",
        "p": "web",
        "dtm": "1621813519722",
        "lang": "pl-PL",
        "sid": "1bc6a951-2b38-4fa0-b9dc-2f3eae8d9833",
        "pp_max": "320"
      },
      "contentType": "application/json",
      "source": {
        "name": "ssc-2.2.1-stdout$",
        "encoding": "UTF-8",
        "hostname": "0.0.0.0"
      },
      "context": {
        "timestamp": "2021-05-23T23:45:19.757Z",
        "ipAddress": "172.20.0.1",
        "useragent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
        "refererUri": "http://localhost:3000/",
        "headers": [
          "Timeout-Access: &lt;function1&gt;",
          "Host: 0.0.0.0:9090",
          "Connection: keep-alive",
          "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
          "Accept: */*",
          "Origin: http://localhost:3000",
          "Referer: http://localhost:3000/",
          "Accept-Encoding: gzip, deflate",
          "Accept-Language: pl-PL, pl;q=0.9, en-US;q=0.8, en;q=0.7",
          "application/json"
        ],
        "userId": "1846ad57-c082-4ac9-9e0e-d9e9dc43e64c"
      }
    },
    "eventType": "page_ping",
    "schema": "iglu:com.snowplowanalytics.snowplow/page_ping/jsonschema/1-0-0",
    "contexts": [
      "iglu:com.snowplowanalytics.snowplow/web_page/jsonschema/1-0-0"
    ],
    "event": {
      "app_id": "fullStackApp-frontend",
      "platform": "web",
      "etl_tstamp": "2021-05-23T23:45:19.836Z",
      "collector_tstamp": "2021-05-23T23:45:19.757Z",
      "dvce_created_tstamp": "2021-05-23T23:45:19.722Z",
      "event": "page_ping",
      "event_id": "259abcd4-f7ee-461e-a11b-8eb93f8d5540",
      "txn_id": null,
      "name_tracker": "sp",
      "v_tracker": "js-3.1.0",
      "v_collector": "ssc-2.2.1-stdout$",
      "v_etl": "snowplow-micro-1.1.1-common-1.4.2",
      "user_id": null,
      "user_ipaddress": "172.20.0.1",
      "user_fingerprint": null,
      "domain_userid": "f9bcfa16-8801-4a5e-a5a6-b02a3e2479df",
      "domain_sessionidx": 13,
      "network_userid": "1846ad57-c082-4ac9-9e0e-d9e9dc43e64c",
      "geo_country": null,
      "geo_region": null,
      "geo_city": null,
      "geo_zipcode": null,
      "geo_latitude": null,
      "geo_longitude": null,
      "geo_region_name": null,
      "ip_isp": null,
      "ip_organization": null,
      "ip_domain": null,
      "ip_netspeed": null,
      "page_url": "http://localhost:3000/",
      "page_title": "React App",
      "page_referrer": null,
      "page_urlscheme": "http",
      "page_urlhost": "localhost",
      "page_urlport": 3000,
      "page_urlpath": "/",
      "page_urlquery": null,
      "page_urlfragment": null,
      "refr_urlscheme": null,
      "refr_urlhost": null,
      "refr_urlport": null,
      "refr_urlpath": null,
      "refr_urlquery": null,
      "refr_urlfragment": null,
      "refr_medium": null,
      "refr_source": null,
      "refr_term": null,
      "mkt_medium": null,
      "mkt_source": null,
      "mkt_term": null,
      "mkt_content": null,
      "mkt_campaign": null,
      "contexts": {
        "schema": "iglu:com.snowplowanalytics.snowplow/contexts/jsonschema/1-0-0",
        "data": [
          {
            "schema": "iglu:com.snowplowanalytics.snowplow/web_page/jsonschema/1-0-0",
            "data": {
              "id": "6af94d85-bee1-4883-98b2-2b5a1107e228"
            }
          }
        ]
      },
      "se_category": null,
      "se_action": null,
      "se_label": null,
      "se_property": null,
      "se_value": null,
      "unstruct_event": null,
      "tr_orderid": null,
      "tr_affiliation": null,
      "tr_total": null,
      "tr_tax": null,
      "tr_shipping": null,
      "tr_city": null,
      "tr_state": null,
      "tr_country": null,
      "ti_orderid": null,
      "ti_sku": null,
      "ti_name": null,
      "ti_category": null,
      "ti_price": null,
      "ti_quantity": null,
      "pp_xoffset_min": null,
      "pp_xoffset_max": 320,
      "pp_yoffset_min": null,
      "pp_yoffset_max": null,
      "useragent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
      "br_name": null,
      "br_family": null,
      "br_version": null,
      "br_type": null,
      "br_renderengine": null,
      "br_lang": "pl-PL",
      "br_features_pdf": null,
      "br_features_flash": null,
      "br_features_java": null,
      "br_features_director": null,
      "br_features_quicktime": null,
      "br_features_realplayer": null,
      "br_features_windowsmedia": null,
      "br_features_gears": null,
      "br_features_silverlight": null,
      "br_cookies": true,
      "br_colordepth": "24",
      "br_viewwidth": 1080,
      "br_viewheight": 1815,
      "os_name": null,
      "os_family": null,
      "os_manufacturer": null,
      "os_timezone": null,
      "dvce_type": null,
      "dvce_ismobile": null,
      "dvce_screenwidth": 1080,
      "dvce_screenheight": 1920,
      "doc_charset": "UTF-8",
      "doc_width": 1400,
      "doc_height": 1800,
      "tr_currency": null,
      "tr_total_base": null,
      "tr_tax_base": null,
      "tr_shipping_base": null,
      "ti_currency": null,
      "ti_price_base": null,
      "base_currency": null,
      "geo_timezone": null,
      "mkt_clickid": null,
      "mkt_network": null,
      "etl_tags": null,
      "dvce_sent_tstamp": "2021-05-23T23:45:19.727Z",
      "refr_domain_userid": null,
      "refr_dvce_tstamp": null,
      "derived_contexts": {},
      "domain_sessionid": "1bc6a951-2b38-4fa0-b9dc-2f3eae8d9833",
      "derived_tstamp": "2021-05-23T23:45:19.752Z",
      "event_vendor": "com.snowplowanalytics.snowplow",
      "event_name": "page_ping",
      "event_format": "jsonschema",
      "event_version": "1-0-0",
      "event_fingerprint": null,
      "true_tstamp": null
    }
  }
```
  
### 3. Java tracker  
  
I used Java tracker to track a custom event - deleting a student from the list.  
I wanted to collect all the data about the student leaving the system, before removing her/him from the database.  
  
  
**Initilizing the tracker**  
  
Code added to my StudentService.java file:  
  
```
void deleteStudent(UUID studentId) {

        // prepare the event to track
        Unstructured deletedStudentEvent = buildDeletedStudentEvent(studentId);
        
        // delete the student
        studentDataAccessService.deleteStudentById(studentId);

        //send the event
        sendDeletedStudentEvent(deletedStudentEvent);

    }

    private Unstructured buildDeletedStudentEvent(UUID studentId) {

        Student studentToDelete = findStudent(studentId);

        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("userFirstName", studentToDelete.getFirstName());
        eventMap.put("userLastName", studentToDelete.getLastName());
        eventMap.put("userEmail", studentToDelete.getEmail());
        eventMap.put("leavingDate", LocalDate.now().toString());
        eventMap.put("leavingTime", LocalTime.now().toString());

        return Unstructured.builder()
                .eventData(new SelfDescribingJson(
                        "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
                        eventMap
                ))
                .build();
    }

    private void sendDeletedStudentEvent(Unstructured deletedStudentEvent) {

        String collectorEndpoint = "http://0.0.0.0:9090";
        String appId = "sp-java-tracker-backend";
        String namespace = "demo";

        BatchEmitter emitter = BatchEmitter.builder()
                .url(collectorEndpoint)
                .requestCallback(new ReqCallBack())
                .bufferSize(5)
                .build();

        final Tracker tracker = new Tracker.TrackerBuilder(emitter, namespace, appId)
                .base64(true)
                .platform(DevicePlatform.ServerSideApp)
                .build();

        tracker.track(deletedStudentEvent);

        emitter.close();
    }
```  
  
**Received by the collector**
   
  
Isolated student data from the full deletedStudentEvent:  
  
```
 "unstruct_event": {
        "schema": "iglu:com.snowplowanalytics.snowplow/unstruct_event/jsonschema/1-0-0",
        "data": {
          "schema": "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
          "data": {
            "leavingTime": "10:27:20.703731",
            "userLastName": "Krasowski",
            "userEmail": "konrad.krasowski@snowplow.com",
            "userFirstName": "Konrad",
            "leavingDate": "2021-05-24"
          }
        }
      }
```

An example of a full deletedStudentEvent: 
  
  
  
```
{
    "rawEvent": {
      "api": {
        "vendor": "com.snowplowanalytics.snowplow",
        "version": "tp2"
      },
      "parameters": {
        "e": "ue",
        "eid": "6334f9a2-e990-4d39-918c-20e22a13961a",
        "aid": "sp-java-tracker-backend",
        "tna": "demo",
        "stm": "1621848440905",
        "tv": "java-0.10.1",
        "p": "srv",
        "dtm": "1621848440704",
        "ue_px": "eyJzY2hlbWEiOiJpZ2x1OmNvbS5zbm93cGxvd2FuYWx5dGljcy5zbm93cGxvdy91bnN0cnVjdF9ldmVudC9qc29uc2NoZW1hLzEtMC0wIiwiZGF0YSI6eyJzY2hlbWEiOiJpZ2x1OmNvbS5zbm93cGxvd2FuYWx5dGljcy5pZ2x1L2FueXRoaW5nLWEvanNvbnNjaGVtYS8xLTAtMCIsImRhdGEiOnsibGVhdmluZ1RpbWUiOiIxMDoyNzoyMC43MDM3MzEiLCJ1c2VyTGFzdE5hbWUiOiJLcmFzb3dza2kiLCJ1c2VyRW1haWwiOiJrb25yYWQua3Jhc293c2tpQHNub3dwbG93LmNvbSIsInVzZXJGaXJzdE5hbWUiOiJLb25yYWQiLCJsZWF2aW5nRGF0ZSI6IjIwMjEtMDUtMjQifX19"
      },
      "contentType": "application/json",
      "source": {
        "name": "ssc-2.2.1-stdout$",
        "encoding": "UTF-8",
        "hostname": "0.0.0.0"
      },
      "context": {
        "timestamp": "2021-05-24T09:27:20.969Z",
        "ipAddress": "172.20.0.1",
        "useragent": "okhttp/4.9.0",
        "refererUri": null,
        "headers": [
          "Timeout-Access: &lt;function1&gt;",
          "Host: 0.0.0.0:9090",
          "Connection: Keep-Alive",
          "Accept-Encoding: gzip",
          "User-Agent: okhttp/4.9.0",
          "application/json"
        ],
        "userId": "f287e4a3-fa58-4d14-9c49-4a2c17c21fe4"
      }
    },
    "eventType": "unstruct",
    "schema": "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
    "contexts": [],
    "event": {
      "app_id": "sp-java-tracker-backend",
      "platform": "srv",
      "etl_tstamp": "2021-05-24T09:27:20.982Z",
      "collector_tstamp": "2021-05-24T09:27:20.969Z",
      "dvce_created_tstamp": "2021-05-24T09:27:20.704Z",
      "event": "unstruct",
      "event_id": "6334f9a2-e990-4d39-918c-20e22a13961a",
      "txn_id": null,
      "name_tracker": "demo",
      "v_tracker": "java-0.10.1",
      "v_collector": "ssc-2.2.1-stdout$",
      "v_etl": "snowplow-micro-1.1.1-common-1.4.2",
      "user_id": null,
      "user_ipaddress": "172.20.0.1",
      "user_fingerprint": null,
      "domain_userid": null,
      "domain_sessionidx": null,
      "network_userid": "f287e4a3-fa58-4d14-9c49-4a2c17c21fe4",
      "geo_country": null,
      "geo_region": null,
      "geo_city": null,
      "geo_zipcode": null,
      "geo_latitude": null,
      "geo_longitude": null,
      "geo_region_name": null,
      "ip_isp": null,
      "ip_organization": null,
      "ip_domain": null,
      "ip_netspeed": null,
      "page_url": null,
      "page_title": null,
      "page_referrer": null,
      "page_urlscheme": null,
      "page_urlhost": null,
      "page_urlport": null,
      "page_urlpath": null,
      "page_urlquery": null,
      "page_urlfragment": null,
      "refr_urlscheme": null,
      "refr_urlhost": null,
      "refr_urlport": null,
      "refr_urlpath": null,
      "refr_urlquery": null,
      "refr_urlfragment": null,
      "refr_medium": null,
      "refr_source": null,
      "refr_term": null,
      "mkt_medium": null,
      "mkt_source": null,
      "mkt_term": null,
      "mkt_content": null,
      "mkt_campaign": null,
      "contexts": {},
      "se_category": null,
      "se_action": null,
      "se_label": null,
      "se_property": null,
      "se_value": null,
      "unstruct_event": {
        "schema": "iglu:com.snowplowanalytics.snowplow/unstruct_event/jsonschema/1-0-0",
        "data": {
          "schema": "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
          "data": {
            "leavingTime": "10:27:20.703731",
            "userLastName": "Krasowski",
            "userEmail": "konrad.krasowski@snowplow.com",
            "userFirstName": "Konrad",
            "leavingDate": "2021-05-24"
          }
        }
      },
      "tr_orderid": null,
      "tr_affiliation": null,
      "tr_total": null,
      "tr_tax": null,
      "tr_shipping": null,
      "tr_city": null,
      "tr_state": null,
      "tr_country": null,
      "ti_orderid": null,
      "ti_sku": null,
      "ti_name": null,
      "ti_category": null,
      "ti_price": null,
      "ti_quantity": null,
      "pp_xoffset_min": null,
      "pp_xoffset_max": null,
      "pp_yoffset_min": null,
      "pp_yoffset_max": null,
      "useragent": "okhttp/4.9.0",
      "br_name": null,
      "br_family": null,
      "br_version": null,
      "br_type": null,
      "br_renderengine": null,
      "br_lang": null,
      "br_features_pdf": null,
      "br_features_flash": null,
      "br_features_java": null,
      "br_features_director": null,
      "br_features_quicktime": null,
      "br_features_realplayer": null,
      "br_features_windowsmedia": null,
      "br_features_gears": null,
      "br_features_silverlight": null,
      "br_cookies": null,
      "br_colordepth": null,
      "br_viewwidth": null,
      "br_viewheight": null,
      "os_name": null,
      "os_family": null,
      "os_manufacturer": null,
      "os_timezone": null,
      "dvce_type": null,
      "dvce_ismobile": null,
      "dvce_screenwidth": null,
      "dvce_screenheight": null,
      "doc_charset": null,
      "doc_width": null,
      "doc_height": null,
      "tr_currency": null,
      "tr_total_base": null,
      "tr_tax_base": null,
      "tr_shipping_base": null,
      "ti_currency": null,
      "ti_price_base": null,
      "base_currency": null,
      "geo_timezone": null,
      "mkt_clickid": null,
      "mkt_network": null,
      "etl_tags": null,
      "dvce_sent_tstamp": "2021-05-24T09:27:20.905Z",
      "refr_domain_userid": null,
      "refr_dvce_tstamp": null,
      "derived_contexts": {},
      "domain_sessionid": null,
      "derived_tstamp": "2021-05-24T09:27:20.768Z",
      "event_vendor": "com.snowplowanalytics.iglu",
      "event_name": "anything-a",
      "event_format": "jsonschema",
      "event_version": "1-0-0",
      "event_fingerprint": null,
      "true_tstamp": null
    }
  }
```
