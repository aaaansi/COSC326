CC = gcc
CFLAGS = -Wall -Wextra -Wpedantic

TARGET = program

all: $(TARGET)

$(TARGET): main.c
	$(CC) $(CFLAGS) -o $(TARGET) main.c

clean:
	rm -f $(TARGET)