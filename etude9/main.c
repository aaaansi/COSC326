#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct Customer
{
    char *firstName;
    char *lastName;
    char *phone;
    char *emailAddress;
};

// finds whether a particular first name exists in the array of pointers to "Customer" structures.
int findFirstName(struct Customer **ss, char *s)
{
    int i, count;
    while (++i < count)
        if (strcmp(ss[i]->firstName, s))
            return 1;
    return 0;
}

// finds whether a particular last name exists in the array of pointers to "Customer" structures.
int findLastName(struct Customer **ss, char *s)
{
    int i, count;
    while (++i < count)
    {
        if (strcmp(ss[i]->lastName, s))
            return 1;
    }
    return 0;
}

// finds whether a particular email address exists in the array of pointers to "Customer" structures.
int findEmail(struct Customer **ss, char *s)
{
    int i, count;
    while (++i < count)
    {
        if (strcmp(ss[i]->emailAddress, s))
            return 1;
    }
    return 0;
}

// finds whether a particular phone number exists in the array of pointers to "Customer" structures.
int findPhoneNumber(struct Customer **ss, char *s)
{
    int i, count;
    while (++i < count)
    {
        if (strcmp(ss[i]->phone, s))
            return 1;
    }
    return 0;
}
// The main function reads input data from a file specified in the command line arguments, stores it in an array of pointers to "Customer" structures, and allows the user to search for specific entries using one of the above-mentioned criteria. The program uses a switch statement to execute the selected operation and displays the result of the operation to the user. The program continues until the user enters the value "0" to exit.
int main(int argc, char *argv[])
{
    int i;
    int count = 0;
    char buffer[10];

    struct Customer **ss = (struct Customer **)malloc(100 * sizeof(struct Customer **));
    struct Customer *s = malloc(sizeof(*s));

    FILE *f;
    f = fopen(argv[1], "r");

    for (i = 0; i < 100; i++)
    {

        s->firstName = (char *)malloc(80 * sizeof(s->firstName[0]));
        s->lastName = (char *)malloc(80 * sizeof(s->firstName[0]));
        s->emailAddress = (char *)malloc(80 * sizeof(s->firstName[0]));

        fscanf(f, "%s %s %d %s", &s->firstName, &s->lastName, &s->phone, &s->emailAddress);

        ss[count] = s;
        count += 1;
        {
            int command = 10;
            while (command != 0)
            {
                char *val = malloc(100 * sizeof(val[0]));
                fgets(buffer, 20, f);
                command = atoi(buffer);
                fgets(buffer, 20, f);
                strcpy(val, buffer);
                switch (command)
                {
                case 1:
                    printf("looking for email %s\n", val);
                    printf("found it? %d\n", findEmail(ss, val));
                    break;
                case 2:
                    printf("looking for firstname %s\n", val);
                    printf("found it? %d\n", findFirstName(ss, val));
                    break;
                case 3:
                    printf("looking for lasname %s\n", val);
                    printf("found it? %d\n", findLastName(ss, val));
                    break;
                case 4:
                    printf("looking for email %s\n", val);
                    printf("found it? %d\n", findPhoneNumber(ss, val));
                default:
                    break;
                }
            }
        }
    }
    fclose(f);
}