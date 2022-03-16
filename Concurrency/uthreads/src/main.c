#include <stdio.h>

extern int add_asm(int, int);

// X86-64 calling conventions
// https://en.wikipedia.org/wiki/X86_calling_conventions

int main() {
    puts("Enter integer two values: ");
    int arg1, arg2;
    scanf("%d %d", &arg1, &arg2);
    // Consume newline
    getchar();  
    int result = add_asm(arg1, arg2);
    printf("add_asm(%d, %d) = %d\n", arg1, arg2, result);
    printf("Result address is 0x%.8X", &result);
    puts("\nPress any key to finish");
    getchar();
    printf("\nResult address is still 0x%.8X", &result);
    return 0;
}