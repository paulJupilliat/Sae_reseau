import socket


def client(host, port):
    sock = socket.socket()
    sock.connect((host, port))
    file = sock.makefile(mode='rw')
    choix = ""
    while choix != "quit":
        choix = input("Entrez votre choix (get / incr / quit): ")
        if choix == "get":
            file.write("get \n")
            file.flush()
            print(file.readline(), end='')
        elif choix == "incr":
            file.write("incr \n")
            file.flush()
            print(file.readline(), end='')
        elif choix == "decr":
            file.write("decr \n")
            file.flush()
            print(file.readline(), end='')
        elif "add" in choix:
            choix = choix.replace('add', "")
            file.write("add %s \n" % val)
            file.flush()
            print(file.readline(), end='')
            try:
                val = int(choix)
            except ValueError:
                print("Valeur invalide")
        else:
            file.write("error \n")
            file.flush()
            print(file.readline(), end='')

    file.write("quit \n")
    file.flush()
    print(file.readline(), end='')
    file.close()
    sock.shutdown(socket.SHUT_RDWR)
    sock.close()


client('localhost', 5554)
