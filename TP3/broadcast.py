import socket
import datetime
import time
import os

def server(port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
    addr = ("255.255.255.255", port)
    while True:
        data = str(datetime.datetime.now())
        data = os.environ["USER"] + " " + data
        sock.sendto(data.encode(), addr)
        time.sleep(1)
    
server(6666)