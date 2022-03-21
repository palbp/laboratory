/**
 * @brief The implementation of the uthread library
 */

#include <stdlib.h>
#include "uthread.h"
#include "list.h"

typedef struct uthread_context {
    uint64_t r15;
    uint64_t r14;
    uint64_t r13;
    uint64_t r12;
    uint64_t rbx;
    uint64_t rbp;
    void (*ret_address)();
} uthread_context_t;

/**
 * @brief The list of uthreads that are READY to execute.
 */
list_entry_t ready_queue;

/**
 * @brief The running uthread.
 */
uthread_t * running_uthread;

/**
 * @brief The uthread used to represent the special case of termination. When this uthread
 * finally runs it means that all uthreads have terminated, and therefore the execution will end.
 */
uthread_t main_thread;

/**
 * @brief Gets the next uthread to be executed, removing it from the ready queue.
 */
uthread_t* remove_next_ready_thread() {
    return is_empty(&ready_queue) ? &main_thread : 
        container_of(remove_from_list_head(&ready_queue), uthread_t, links);
}

//////////// Implementation of the public functions

uthread_t* ut_create(void (*thread_code)()) {
    uthread_t* pthread = malloc(STACK_SIZE);
    uthread_context_t* pctx = (uthread_context_t*)
        (((uint8_t*)pthread + STACK_SIZE) - sizeof(uthread_context_t));
    pctx->rbp = 0;
    pctx->ret_address = thread_code;
    pthread->rsp = (uint64_t) pctx;

    insert_at_list_tail(&ready_queue, &pthread->links);
    return pthread;
}

void ut_exit() {
    // TODO: Fix memory leak
    context_switch(running_uthread, remove_next_ready_thread());
}

void ut_yield() {
    if (!is_empty(&ready_queue)) {
        insert_at_list_tail(&ready_queue, &(running_uthread->links));
        context_switch(running_uthread, remove_next_ready_thread());
    }
}

void ut_init() {
    init_list(&ready_queue);
}

void ut_run() {
    running_uthread = &main_thread;
    context_switch(running_uthread, remove_next_ready_thread());
}

void ut_end() {
    printf("Is ready-queue empty? %s\n", is_empty(&ready_queue) ? "true" : "false");
    // No cleanup needed for now
}