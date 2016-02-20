# Notes on the HC-05 / HC-06 Bluetooth module

Different variations of the bluetooth module come preconfigured with different baudrates. The AccelRC controller board expects the bluetooth module to work on 9600bps, 1 stop bit, 0 parity bit. 

To change the configuration on the bluetooth module:
* Connect the module to a USB to Serial adapter, for instance an Arduino Uno with the MCU removed.
* Before powering on, press (and keep pressed) the little button on the BT board.
* Plug the USB to Serial adapter in your computer (while pressing the button on the BT board)
* If everything is powered on, release the button. The LED on the BT module should now flash with a 2 sec interval.
* Open a serial console on your computer. Select the right port and use 38400bps. I had to use both newline and carriage return, although some sources report one shouldn't use either of them. If things do not work, try different configurations.
* Send the command "AT+VERSION?". This should give some version information. If it doesn't, don't bother continuing. Figure out what's wrong first :)
* The following commans could be useful:
	- Reset to factory defaults: "AT+ORGL"
	- Change name: "AT+NAME=some-name"
	- Change passcode: "AT+PSWD=1337"
	- Change baud rate, stop bit, parity: "AT+UART=9600,1,0"