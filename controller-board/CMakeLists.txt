cmake_minimum_required(VERSION 2.6)

project(AccelRC)

set(AVRDUDE_PROGRAMMER_FLAGS -cusbasp)
set(AVR_MCU "atmega328p")
set(AVR_FCPU "16000000")
set(ARDUINO_VARIANT "standard")

add_definitions(-DARDUINO_ARCH_AVR)

include(cmake/arduino-1.6.cmake)
include(cmake/avr-toolchain.cmake)
include(cmake/download-nanopb.cmake)

set(ARDUINO_SOURCE_FILES

        # Overwrite ARDUINO_SOURCE_FILES variable with just the code we need, for a smaller binary.
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

        ${NANOPB_SRC}
        )

include_directories("${ARDUINO_LIB_DIR}/Servo/src")

set(ACCELRC_SOURCE_FILES
        ${CMAKE_SOURCE_DIR}/src/main.cpp
        ${ARDUINO_SOURCE_FILES}
        )



add_executable(
        ${CMAKE_PROJECT_NAME}
        ${ACCELRC_SOURCE_FILES})
