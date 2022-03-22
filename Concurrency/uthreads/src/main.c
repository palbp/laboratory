#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "uthread.h"

void do_steps(void * args) {
    char * id = (char*) args;
    printf("%s: step 1\n", id);
    ut_yield();
    printf("%s: step 2\n", id);
    ut_yield();
    printf("%s: step 3\n", id);
}

void say_hi(void *args) {
    char * id = (char*) args;
    printf("%s: Hi all! 1\n", id);
}

int main() {

    puts("main: DEMO STARTS");
    ut_init();

    ut_create(do_steps, "T1");
    ut_create(do_steps, "T2");
    ut_create(say_hi, "T3");
    ut_run();
    
    ut_end();
    puts("main: DEMO ENDS");
    return 0;
}