/*
  RC control by Bluetooth
  Bluetooth module HC-06
  modified 12 Jan 2016
  by Frans Damave
*/
#include <Arduino.h>
#include <Servo.h>
#include <HardwareSerial.h>

Servo myServo1;
Servo myServo2;
Servo myServo3;
Servo myServo4;


int angle1;
int angle2;
int angle3;
int angle4;

unsigned long lastSerial;
unsigned long SerialOut;

void setup() {
    // initialize the serial communications:
    Serial.begin(9600);
    myServo1.attach(3);
    myServo2.attach(4);
    myServo3.attach(5);
    myServo4.attach(6);
}

void loop() {

    if (millis() - 1000 > lastSerial) {
        myServo1.write(90);
        myServo2.write(90);
        myServo3.write(90);
        myServo4.write(90);
    }

    if (millis() - 10000 > SerialOut) {
        int sensorValue = analogRead(A0);
        Serial.println(sensorValue);
        SerialOut = millis();
    }

    if (Serial.available()) {
        lastSerial = millis();
        // digitalWrite(13, LOW);
        // look for the next valid integer in the incoming serial stream:
        int servo1 = Serial.parseInt();
        int servo2 = Serial.parseInt();
        int servo3 = Serial.parseInt();
        int servo4 = Serial.parseInt();

        if (servo1 >= 500 && servo1 <= 2500)
            angle1 = map(servo1, 500, 2500, 0, 179);
        if (servo2 >= 500 && servo2 <= 2500)
            angle2 = map(servo2, 500, 2500, 0, 179);
        if (servo3 >= 500 && servo3 <= 2500)
            angle3 = map(servo3, 500, 2500, 0, 179);
        if (servo4 >= 500 && servo4 <= 2500)
            angle4 = map(servo4, 500, 2500, 0, 179);
        if (Serial.read() == '\n') {
            myServo1.write(angle1);
            myServo2.write(angle2);
            myServo3.write(angle3);
            myServo4.write(angle4);
        }
    }
}


int main(void) {
    // Mandatory init
    init();
    setup();
    while (true)
        loop();
}