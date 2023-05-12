#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Customer
{
    char *firstName;
    char *lastName;
    char *phone;
    char *emailAddress;
};

int findFirstName(struct Customer **ss, char *s, int count);
int findLastName(struct Customer **ss, char *s, int count);
int findEmail(struct Customer **ss, char *s, int count);
int findPhoneNumber(struct Customer **ss, char *s, int count);
void freeCustomerData(struct Customer **ss, int count);

int main(int argc, char *argv[])
{
    FILE *f;
    struct Customer **ss;
    int count = 0;
    char buffer[100];
    int command = 10;

    f = fopen(argv[1], "r");
    if (f == NULL)
    {
        printf("Error opening file.\n");
        return 1;
    }

    ss = (struct Customer **)malloc(100 * sizeof(struct Customer *));
    if (ss == NULL)
    {
        printf("Memory allocation failed.\n");
        fclose(f);
        return 1;
    }

    while (count < 100 && fscanf(f, "%s %s %s %s", buffer, buffer, buffer, buffer) == 4)
    {
        struct Customer *s = (struct Customer *)malloc(sizeof(struct Customer));
        if (s == NULL)
        {
            printf("Memory allocation failed.\n");
            freeCustomerData(ss, count);
            fclose(f);
            return 1;
        }

        s->firstName = strdup(buffer);
        s->lastName = strdup(buffer);
        s->phone = strdup(buffer);
        s->emailAddress = strdup(buffer);

        ss[count++] = s;
    }
    fclose(f);

    while (command != 0)
    {
        printf("\nEnter the search option:\n");
        printf("1. Search by email\n");
        printf("2. Search by first name\n");
        printf("3. Search by last name\n");
        printf("4. Search by phone number\n");
        printf("0. Exit\n");
        printf("Option: ");

        fgets(buffer, sizeof(buffer), stdin);
        command = atoi(buffer);

        if (command == 0)
            break;

        printf("Enter the search term: ");
        fgets(buffer, sizeof(buffer), stdin);

        switch (command)
        {
        case 1:
            printf("Searching by email: %s", buffer);
            printf("Found it? %s\n", findEmail(ss, buffer, count) ? "Yes" : "No");
            break;
        case 2:
            printf("Searching by first name: %s", buffer);
            printf("Found it? %s\n", findFirstName(ss, buffer, count) ? "Yes" : "No");
            break;
        case 3:
            printf("Searching by last name: %s", buffer);
            printf("Found it? %s\n", findLastName(ss, buffer, count) ? "Yes" : "No");
            break;
        case 4:
            printf("Searching by phone number: %s", buffer);
            printf("Found it? %s\n", findPhoneNumber(ss, buffer, count) ? "Yes" : "No");
            break;
        default:
            printf("Invalid option. Please try again.\n");
            break;
        }
    }

    freeCustomerData(ss, count);
    return 0;
}

int findFirstName(struct Customer **ss, char *s, int count)
{
    for (int i = 0; i < count; i++)
    {
        if (strcmp(ss[i]->firstName, s) == 0)
            return 1;
    }
    return 0;
}

int findLastName(struct Customer **ss, char *s, int count)
{
    for (int i = 0; i < count; i++)
    {
        if (strcmp(ss[i]->lastName, s) == 0)
            return 1;
    }
    return 0;
}

int findEmail(struct Customer **ss, char *s, int count)
{
    for (int i = 0; i < count; i++)
    {
        if (strcmp(ss[i]->emailAddress, s) == 0)
            return 1;
    }
    return 0;
}

int findPhoneNumber(struct Customer **ss, char *s, int count)
{
    for (int i = 0; i < count; i++)
    {
        if (strcmp(ss[i]->phone, s) == 0)
            return 1;
    }
    return 0;
}

void freeCustomerData(struct Customer **ss, int count)
{
    for (int i = 0; i < count; i++)
    {
        free(ss[i]->firstName);
        free(ss[i]->lastName);
        free(ss[i]->phone);
        free(ss[i]->emailAddress);
        free(ss[i]);
    }
    free(ss);
}
