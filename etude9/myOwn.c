#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE_LEN 1024
#define MAX_FIELD_LEN 256

typedef struct
{
    char first_name[MAX_FIELD_LEN];
    char last_name[MAX_FIELD_LEN];
    char email[MAX_FIELD_LEN];
    char phone[MAX_FIELD_LEN];
} Client;

void search_by_field(char field, char *value, FILE *fp)
{
    char line[MAX_LINE_LEN];
    Client client;
    int found = 0;

    while (fgets(line, MAX_LINE_LEN, fp))
    {
        sscanf(line, "%s %s %s %s", client.first_name, client.last_name, client.email, client.phone);

        switch (field)
        {
        case 'f':
            if (strcmp(client.first_name, value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        case 'l':
            if (strcmp(client.last_name, value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        case 'e':
            if (strcmp(client.email, value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        case 'p':
            if (strcmp(client.phone, value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        }
    }

    if (!found)
    {
        printf("(Client does not exist)\n");
    }
}

int main(int argc, char *argv[])
{
    char *filename;
    FILE *fp;
    char search_field;
    char search_value[MAX_FIELD_LEN];

    if (argc != 2)
    {
        printf("Usage: %s <filename>\n", argv[0]);
        return 1;
    }

    filename = argv[1];
    fp = fopen(filename, "r");
    if (!fp)
    {
        printf("Error opening file %s\n", filename);
        return 1;
    }

    printf("Enter f to search by first name: \n      l to search by last name: \n      e to search by email: \n      p to search by phone number: \n      x to Exit:\n");
    scanf("%c", &search_field);
    if (search_field == 'x')
    {
        printf("Exit Success!");
        return 0;
    }

    printf("Enter search value: ");
    scanf("%s", search_value);

    search_by_field(search_field, search_value, fp);

    fclose(fp);
    return 0;
}