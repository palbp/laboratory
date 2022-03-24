#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "uthread.h"

void do_steps(void * args) {
    char * id = (char*) args;
    printf("%s: step 1\n", id);
    ut_yield();
    printf("%s: step 2 (sleeping now)\n", id);
    ut_sleep(1);
    printf("%s: step 3\n", id);
}

void say_hi(void *args) {
    char * id = (char*) args;
    printf("%s: Hi all! (Going to sleep)\n", id);
    ut_sleep(10);
    printf("%s: Bye!\n", id);
}

void sleeper(void *args) {
    char * id = (char*) args;
    printf("%s: starts\n", id);

    for(int i=0; i<5; ++i) {
        printf("%s: Going to sleep\n", id);
        ut_sleep(1);
        printf("%s: done\n", id);
    }
    printf("%s: ends\n", id);
}

int main() {

    puts("main: DEMO STARTS");
    ut_init();

    //ut_create(sleeper, "T0");
    ut_create(do_steps, "T1");
    ut_create(do_steps, "T2");
    ut_create(say_hi, "T3");
    ut_run();
    
    ut_end();
    puts("main: DEMO ENDS");
    return 0;
}