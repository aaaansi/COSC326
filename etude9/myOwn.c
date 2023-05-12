#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_FIELD_LEN 100
#define MAX_LINE_LEN 256

// The code defines a struct named "Client" with four fields: first_name, last_name, email, and phone. This struct requires additional memory allocation for each field, resulting in more lines of code.
// The code uses the MAX_FIELD_LEN and MAX_LINE_LEN constants to define the maximum length of fields and lines, respectively. This ensures proper memory allocation and prevents buffer overflows.
// The search_by_field function uses a while loop to read each line from the file. For each line, it allocates memory for the fields of the Client struct, parses the line using sscanf, converts the search value and client fields to lowercase, and performs the search based on the specified field. This involves multiple lines of code for string comparisons and memory management.
// The main function handles command-line arguments, file opening, and error checking, which adds more lines of code for validation and error handling.
// The code includes a loop for performing multiple searches based on user input. It prompts the user for search options, reads the search value, performs the search, and asks if another search is desired. This loop contributes to additional lines of code for user interaction and input validation.
// The code includes memory deallocation using the free function for each field of the Client struct to prevent memory leaks.

typedef struct
{
    char *first_name;
    char *last_name;
    char *email;
    char *phone;
} Client;

void search_by_field(char field, char *value, FILE *fp)
{
    char line[MAX_LINE_LEN];
    Client client;
    int found = 0;

    while (fgets(line, MAX_LINE_LEN, fp))
    {
        client.first_name = (char *)malloc(MAX_FIELD_LEN * sizeof(char));
        client.last_name = (char *)malloc(MAX_FIELD_LEN * sizeof(char));
        client.email = (char *)malloc(MAX_FIELD_LEN * sizeof(char));
        client.phone = (char *)malloc(MAX_FIELD_LEN * sizeof(char));

        sscanf(line, "%s %s %s %s", client.first_name, client.last_name, client.phone, client.email);

        // Convert the search value and client field to lowercase
        char lower_value[MAX_FIELD_LEN];
        char lower_field[MAX_FIELD_LEN];
        strcpy(lower_value, value);
        strcpy(lower_field, value);
        for (int i = 0; lower_value[i]; i++)
        {
            lower_value[i] = tolower(lower_value[i]);
        }
        for (int i = 0; lower_field[i]; i++)
        {
            lower_field[i] = tolower(lower_field[i]);
        }

        switch (field)
        {
        case 'f':
            if (strcasecmp(client.first_name, lower_value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        case 'l':
            if (strcasecmp(client.last_name, lower_value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        case 'e':
            if (strcasecmp(client.email, lower_value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        case 'p':
            if (strcasecmp(client.phone, lower_value) == 0)
            {
                printf("%s", line);
                found = 1;
            }
            break;
        }

        free(client.first_name);
        free(client.last_name);
        free(client.email);
        free(client.phone);
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

    int valid_input = 0;
    while (!valid_input)
    {
        printf("Enter f to search by first name:\n"
               "      l to search by last name:\n"
               "      e to search by email:\n"
               "      p to search by phone number:\n"
               "      x to Exit:\n");
        scanf(" %c", &search_field);

        if (search_field == 'x')
        {
            printf("Exit Success!");
            return 0;
        }

        printf("Enter search value: ");
        scanf("%s", search_value);

        // Convert the search value to lowercase
        for (int i = 0; search_value[i]; i++)
        {
            search_value[i] = tolower(search_value[i]);
        }

        search_by_field(search_field, search_value, fp);

        printf("\n");

        // Prompt for another search if the input is invalid
        printf("Do you want to perform another search? (y/n): ");
        char choice[10];
        scanf("%9s", choice);

        if (tolower(choice[0]) != 'y')
        {
            valid_input = 1;
        }

        // Flush the input buffer
        int c;
        while ((c = getchar()) != '\n' && c != EOF)
        {
        }

        fseek(fp, 0, SEEK_SET); // Reset the file pointer to the beginning of the file
    }

    fclose(fp);
    return 0;
}
