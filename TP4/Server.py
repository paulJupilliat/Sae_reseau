import socket
import threading


class Server:
    def __init__(self):
        self.counter = 0

    def mainServer(self, port):
        sock = socket.socket()
        sock.bind(('0.0.0.0', port))
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        sock.listen(10)
        while True:
            cli, addr = sock.accept()
            sess = Session(self, cli)
            sess.start()


class Session(threading.Thread):

    def __init__(self, server, sock):
        threading.Thread.__init__(self)
        self.server = server
        self.socket = sock
        self.file = sock.makefile(mode='rw')

    def mainSession(self):
        while True:
            line = self.file.readline().strip()
            if line == "get":
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            elif line == "quit":
                self.file.write("quit \n")
                self.file.flush()
                break
            elif line == "incr":
                self.server.counter += 1
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            elif line == "decr":
                self.server.counter -= 1
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            elif "add" in line:
                line = line.replace('add', "")
                line = line.replace(' ', "")
                print(line)
                val = int(line)
                self.server.counter += val
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            else:
                print(line)
                self.file.write("error \n")
                self.file.flush()

        self.file.close()
        self.socket.shutdown(socket.SHUT_RDWR)
        self.socket.close()

    def run(self):
        while True:
            line = self.file.readline().strip()
            if line == "get":
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            elif line == "quit":
                self.file.write("quit \n")
                self.file.flush()
                break
            elif line == "incr":
                self.server.counter += 1
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            elif line == "decr":
                self.server.counter -= 1
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            elif "add" in line:
                line = line.replace('add', "")
                line = line.replace(' ', "")
                print(line)
                val = int(line)
                self.server.counter += val
                self.file.write("val %d \n" % self.server.counter)
                self.file.flush()
            else:
                print(line)
                self.file.write("error \n")
                self.file.flush()

        self.file.close()
        self.socket.shutdown(socket.SHUT_RDWR)
        self.socket.close()

Server().mainServer(5554)
