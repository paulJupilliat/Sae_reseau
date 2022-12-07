#!/usr/bin/python3

import socket
from sys import argv

BUFSIZE = 1024


def client(port=argv[2], host=argv[1]): 
    """ Client pour voir le dernier message envoyé par le serveur 
    Args:
        port (int): port du serveur
        host (str): adresse du serveur
    """

    sock = socket.socket(type = socket.SOCK_DGRAM)
    addr = (host, port)
    sock.sendto(b"", addr)
    data = sock.recv(BUFSIZE)
    print(data.decode(), end = "")

client(5558)

