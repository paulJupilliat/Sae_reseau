#!/usr/bin/python3

import socket
from sys import argv

BUFSIZE = 1024

def ext_client(port, host=argv[1]):
    """ Client pour voir le dernier message envoyé par le serveur 
    Args:
        port (int): port du serveur
        host (str): adresse du serveur
    """
    sock = socket.socket(type=socket.SOCK_DGRAM)
    addr = (host, port)
    match argv[2]:
        case "user":
            sock.sendto("user".encode(), addr)
        case "date":
            sock.sendto("date".encode(), addr)
    data = sock.recv(BUFSIZE)
    print(data.decode())




ext_client(5558)
