cmake_minimum_required(VERSION 2.6)

project(AccelRC_Controller_Board)

set(ARDUINO_ROOT "/Applications/Arduino.app/Contents/Java")

set(ARDUINO_PROTOCOL "usbasp")
set(ARDUINO_VARIANT "standard")
set(ARDUINO_MCU "atmega328p")
set(ARDUINO_FCPU "16000000")

set(ARDUINO_LIB_DIR "${ARDUINO_ROOT}/libraries/")

include(${CMAKE_SOURCE_DIR}/Arduino-CMake-Template/cmake/arduino-1.6.cmake)

set(ARDUINO_SOURCE_FILES
        #        ${ARDUINO_CORE_DIR}/wiring_pulse.S

        ${ARDUINO_CORE_DIR}/hooks.c
        ${ARDUINO_CORE_DIR}/wiring.c
        ${ARDUINO_CORE_DIR}/wiring_analog.c
        ${ARDUINO_CORE_DIR}/wiring_digital.c
        ${ARDUINO_CORE_DIR}/HardwareSerial.cpp
        ${ARDUINO_CORE_DIR}/HardwareSerial0.cpp
        ${ARDUINO_CORE_DIR}/Print.cpp
        ${ARDUINO_CORE_DIR}/Stream.cpp
        ${ARDUINO_CORE_DIR}/WMath.cpp
        ${ARDUINO_CORE_DIR}/WString.cpp
        ${ARDUINO_CORE_DIR}/abi.cpp
        ${ARDUINO_CORE_DIR}/new.cpp

        ${ARDUINO_LIB_DIR}/Servo/src/avr/Servo.cpp
        )


include_directories("${ARDUINO_LIB_DIR}/Servo/src")

include_directories(include)

set(MYPROJECT_SOURCE_FILES
        ${CMAKE_SOURCE_DIR}/src/main.cpp
        ${ARDUINO_SOURCE_FILES}
        )

add_definitions( -DARDUINO_ARCH_AVR)


add_executable(firmware ${MYPROJECT_SOURCE_FILES})
