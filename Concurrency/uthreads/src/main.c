#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "uthread.h"

void thread1_code() {
    puts("T1: step 1");
    ut_yield();
    puts("T1: step 2");
    ut_yield();
    puts("T1: step 3");
    ut_exit();
}

void thread2_code() {
    puts("T2: step 1");
    ut_yield();
    puts("T2: step 2");
    ut_yield();
    puts("T2: step 3");
    ut_exit();
}

void thread3_code() {
    puts("T3: single step");
    ut_exit();
}

int main() {

    puts("main: DEMO STARTS");
    ut_init();

    ut_create(thread1_code);
    ut_create(thread2_code);
    ut_create(thread3_code);
    ut_run();
    
    ut_end();
    puts("main: DEMO ENDS");
    return 0;
}