# 停車場APP
停車場預約車位APP，採用Android Studio的Java語言進行開發。該APP提供預約和查看車位，以及檢舉功能，並且該APP系統採用會員機制，需要事先註冊該APP會員，才能進行車位預約。
# 預覽畫面
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/jDW5OGN0GFU/0.jpg)](https://www.youtube.com/watch?v=jDW5OGN0GFU)
# 功能
* 提供登入和註冊功能
* 提供帳號密碼和車牌號修改
* 提供停車場車位偵測顯示
* 產生QRcode介面，提供車輛進出柵欄時模擬
# 建置作業
### 車位感測模組
* **感測器和APP的資料庫串接PHP檔，和QRcode的網頁檔，存放在backend 檔案裡。**
* **感測器**: **光敏電阻**
* **WiFi模組**: **Esp8266**
* **接線圖** :
    ![](https://i.imgur.com/Qy5gN4B.png)
* **Arduino IDE 程式碼** : 
```
#include <SoftwareSerial.h>
#define DEBUG true
SoftwareSerial ESP8266(4,5);//"RX=5,TX=4"
String ssid="iPhone";
String password="henry143";
String ip="連線Server";
int photocellPin = 光敏感測器腳位 ;                         
int sensorValue = 0;
void setup() 
{
  Serial.begin(9600);  
  ESP8266.begin(9600);
  sendData("AT+RST\r\n",2000,DEBUG);         //sendData為一個函式,用來發送"AT指令"用的
  sendData("AT+CWMODE=1\r\n",1000,DEBUG);    //當client端,設置為1
  sendData("AT+CIPMUX=1\r\n",1000,DEBUG);    //設置為一對一連線 
  pinMode(photocellPin,INPUT);               //光敏感測器,偵測輸入
}
void loop() 
{
 String data="",cmd="";
 sensorValue = analogRead(photocellPin);
 sendData("AT+RST\r\n",2000,DEBUG);         //每次傳送都必須重新啟動一次,因為成功傳送後esp8266就會關閉(closed)所以才要在loop迴圈裡不斷的執行
 while (!connectWifi(ssid,password))        //connectWifi也是函式,用來wifi連結的指令執行,連wifi放loop迴圈的原因是因為,避免esp8266突然斷網,沒傳送到資料,放在迴圈裡每執行一次,就檢查一次
  {
    Serial.println("Connecting WiFi ... failed");
    delay(2000);
  }
 sendData("AT+CIFSR\r\n",1000,DEBUG);    
 delay(1000); 
 sendData("AT+CIPSTART=\"TCP\",\""+ip+"\",80\r\n", 1000, DEBUG);//TCP才能成功傳送
 data="";
  if(sensorValue>300)
  {
    data="o";
  }
  else
  {
    data="f";
  }
  String Get=String("")+ "GET /sensor_2.php?data_2=" + data+ "\r\n"; //傳給php的格式,存入get字串
  cmd="AT+CIPSEND=";
  cmd += Get.length();
  cmd += "\r\n";
  sendData(cmd,1000,DEBUG);
  sendData(Get,500,DEBUG); //get傳給php必須傳兩次才能成功
  sendData(Get,500,DEBUG);
  Serial.println(Get);
  delay(5000);
}
boolean connectWifi(String ssid, String password) 
{
  String c_response=sendData("AT+CWJAP=\"" + ssid + "\",\"" + password + "\"\r\n",8000,DEBUG);//電腦會先把等號右邊的指令在sendData()函式中執行完,再回傳結果存入res
  c_response.replace("\r\n","");                       //"回到首行,且換行",被取代為"空白";讓監視視窗不會一直跳下一行
  if (c_response.indexOf("OK") != -1)                  //在監視式窗內,判斷是否有"ok"的字串("indexOf()"用法:若有，就回傳第一個符合元素的索引值；若無，回傳-1)
  {
  return true;
  }
  else
  {
  return false;
  }
}
String sendData(String command, const int timeout, boolean debug) 
{
  String response="",r_connectWifi="";                                
  ESP8266.print(command);                       //print代表"輸出"
  long int time=millis();                       //"millis()"這函式用來取得arduino執行程式到目前過了幾個milliseconds(毫秒)
  while ((time + timeout) > millis())           //"time"是執行這函式的起始時間,而"timeout"是傳過來的時間,"while(millis()):判斷首次=time;判斷第二次之後=time+執行迴圈的秒數"       
  {                                             //"(time + timeout) > millis()"這段是為了控制讀取的時間,避免過多時間讀取到指令以外的東西
    while(ESP8266.available())                  //用來判斷有無資料,"available()"函式是一個一個字讀取,直到所有指令讀完
  {                                             //read()這函式是一個字一個字讀取,所以用while迴圈執行,讀一個存一個,再丟到string字串裡
    char c=ESP8266.read();                      //char只能存一個字元,所以當讀到一個字元時就會接在response後面 ,成為一個完整字串
    response += c;                         
  }
  }
  if (debug) 
  {
  Serial.print(response);                     //監視視窗觀看用的
  }
    r_connectWifi=response;                    
    return r_connectWifi;                     //"r_connectWifi"傳回到connectWifi()函式,讓他可以判斷wifi是否有連上               
}
```
* Entity Relationship Diagram : 
    ![](https://i.imgur.com/0JZSECI.png)

# 開發環境
* Android Studio
* Arduino ide
