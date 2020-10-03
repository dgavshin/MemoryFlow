# Memory Flow

To date, no operating system is able to track the behavior of the any <b>signal*</b> and determine precisely 
whether it is ordinary or called in order to steal confidential information. 
This project implements an attack on this vulnerability. 
Under the <b>signal*</b>, I define any action that has several states. 
For example:
1) an open and closed socket
2) a working and a non-working process
3) a created and deleted file, a mounted and unmounted partition, and so on.

The <b> purpose </b> of the attack is confidential information \
The <b> initiator </b> of the attack is a user who has fairly high rights in the system \
The <b> essence </b> of the attack is to quietly lower the file’s access rights by means of <b>signal*</b> transmission.
