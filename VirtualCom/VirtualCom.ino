#include <SFE_BMP180.h>
#include <Wire.h>
SFE_BMP180 pressure;
#define ALTITUDE 1655.0

void setup() {
  Serial.begin(9600);
  pressure.begin();
  pinMode(3, INPUT);
  pinMode(2, OUTPUT);
}
//###################################################################
// 温湿度
void initDHT11() {
  pinMode(13, OUTPUT);
   // 拉低总线持续>18ms
   digitalWrite(13, LOW);
   delay(30);
   
   // 拉高总线持续20-40us
   digitalWrite(13, HIGH);
   delayMicroseconds(40);
   
   // 设置输入的上拉模式
   pinMode(13, INPUT_PULLUP);
   // 此时信号为高 等传感器发送来低信号
   while(digitalRead(13) == HIGH) {
     // 传感器发送响应拉低总线80us
     delayMicroseconds(80);
   }
   // 此时信号为低 等传感器发送高信号
   if (digitalRead(13) == LOW) {
     delayMicroseconds(80);
   }
   // 初始化完成 传感器开始发送数据
}
unsigned char readByByte() {
  unsigned char i, dat = 0;
  unsigned int j;

  pinMode(13, INPUT_PULLUP);
  delayMicroseconds(2);

  for(i = 0; i < 8; i++){
      while(digitalRead(13) == LOW);//等待50us
      delayMicroseconds(50);//需要不断更改时间低于50，高于30之间测试

      if(digitalRead(13) == HIGH){
          dat |= (1 << (7-i)); //高位在前低位在后
      }

      while(digitalRead(13) == HIGH);//数据"1" 等待下一位的接收
      
  }
  return dat;
}
//###################################################################

byte inByte;

void loop() {
      
  if (Serial.available() > 0) {
    inByte = Serial.read();
    if (inByte == 'a') {
      //-----------------------------------------------------------------
      // 温湿度
      initDHT11();
      unsigned char a1 = readByByte();
      unsigned char a2 = readByByte();
      unsigned char a3 = readByByte();
      unsigned char a4 = readByByte();
      unsigned char a5 = readByByte();
      delayMicroseconds(50);//等待50us拉低
      pinMode(13, OUTPUT);//设置为输出引脚
      digitalWrite(13,HIGH);//释放总线
      Serial.print(a1 * 2);
      Serial.print("#");
      Serial.print(a3 * 2);
      Serial.print("#");
      //delay(500);
      //-----------------------------------------------------------------
      // 气压
      char status;
      double T,P,p0,a;
      status = pressure.startTemperature();
      if (status != 0)
      {
        delay(status);
        status = pressure.getTemperature(T);
        if (status != 0)
        {
          status = pressure.startPressure(3);
          if (status != 0)
          {
            delay(status);
            status = pressure.getPressure(P,T);
            if (status != 0)
            {
              Serial.print(P,0);
              Serial.print("#");
              //Serial.print(P*0.0295333727,2);
              //Serial.println(" inHg");
            }
            else Serial.println("error retrieving pressure measurement\n");
          }
          else Serial.println("error starting pressure measurement\n");
        }
        else Serial.println("error retrieving temperature measurement\n");
      }
      else Serial.println("error starting temperature measurement\n");
      //delay(500);
      //-----------------------------------------------------------------
      // 光线亮度
      int sensorValue = 1023 - analogRead(A0);
      Serial.print(sensorValue);
      Serial.print("#");
      //delay(500);
      //-----------------------------------------------------------------
      // 超声波测距
      digitalWrite(2, LOW);
      delayMicroseconds(2);
      digitalWrite(2, HIGH);// 拉高
      delayMicroseconds(10);// 保持10us
      digitalWrite(2, LOW);
    
      int distance = pulseIn(3,HIGH); // 读取接收脉冲时间?ms
      //Serial.println(distance);
      int cm = distance / 58;
      int inch = distance / 54;
      Serial.print(cm + 1);
      //-----------------------------------------------------------------
      Serial.print("%");
    }
  }
  delay(200);
}
////-----------------------------------------------------------------
//      // 温湿度
//      initDHT11();
//      unsigned char a1 = readByByte();
//      unsigned char a2 = readByByte();
//      unsigned char a3 = readByByte();
//      unsigned char a4 = readByByte();
//      unsigned char a5 = readByByte();
//      delayMicroseconds(50);//等待50us拉低
//      pinMode(13, OUTPUT);//设置为输出引脚
//      digitalWrite(13,HIGH);//释放总线
//      Serial.print(a1 * 2);
//      Serial.print("#");
//      Serial.print(a3 * 2);
//      Serial.print("#");
//      //delay(500);
//      //-----------------------------------------------------------------
//      // 气压
//      char status;
//      double T,P,p0,a;
//      status = pressure.startTemperature();
//      if (status != 0)
//      {
//        delay(status);
//        status = pressure.getTemperature(T);
//        if (status != 0)
//        {
//          status = pressure.startPressure(3);
//          if (status != 0)
//          {
//            delay(status);
//            status = pressure.getPressure(P,T);
//            if (status != 0)
//            {
//              Serial.print(P,0);
//              Serial.print("#");
//              //Serial.print(P*0.0295333727,2);
//              //Serial.println(" inHg");
//            }
//            else Serial.println("error retrieving pressure measurement\n");
//          }
//          else Serial.println("error starting pressure measurement\n");
//        }
//        else Serial.println("error retrieving temperature measurement\n");
//      }
//      else Serial.println("error starting temperature measurement\n");
//      //delay(500);
//      //-----------------------------------------------------------------
//      // 光线亮度
//      int sensorValue = 1023 - analogRead(A0);
//      Serial.print(sensorValue);
//      Serial.print("#");
//      //delay(500);
//      //-----------------------------------------------------------------
//      // 超声波测距
//      digitalWrite(2, LOW);
//      delayMicroseconds(2);
//      digitalWrite(2, HIGH);// 拉高
//      delayMicroseconds(10);// 保持10us
//      digitalWrite(2, LOW);
//    
//      int distance = pulseIn(3,HIGH); // 读取接收脉冲时间?ms
//      //Serial.println(distance);
//      int cm = distance / 58;
//      int inch = distance / 54;
//      Serial.print(cm + 1);
//      //-----------------------------------------------------------------
