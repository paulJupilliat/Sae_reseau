#!/usr/bin/python3

import socket
import datetime
import os

BUFSIZE = 1024

def server(port):
    
    sock = socket.socket(type = socket.SOCK_DGRAM)
    sock.bind(("0.0.0.0", port))
    while True:
        user = os.environ["USER"]
        data, addr = sock.recvfrom(BUFSIZE)
        date = datetime.datetime.now()
        print(data)
        print("Message reçu de %s:%s, est: %s"%(addr[0], addr[1], data))
        match data.decode():
            case "user":
                data = user
            case "date":
                data = "Date: %s"%date
        sock.sendto(data.encode(), addr)

server(5558)