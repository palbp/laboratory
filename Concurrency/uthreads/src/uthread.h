/**
 * @brief The public interface of the uthread library
 */

#ifndef UTHREAD_H
#define UTHREAD_H

#include <stdint.h>
#include "list.h"

#define STACK_SIZE (8*4096)

typedef struct uthread {
    uint64_t rsp;
    list_entry_t links;
} uthread_t;

/**
 * Switches execution from pthread1 to pthread2.
 */
extern void context_switch(uthread_t* pthread1, uthread_t* pthread2);

/**
 * @brief Creates a uthread with the specified behaviour.
 * @param thread_code   The uthread's behaviour (its code)
 * @return the descriptor of the created uthread
 */
uthread_t* ut_create(void (*thread_code)());

/**
 * @brief Terminates the calling uthread.
 */
void ut_exit();

/**
 * @brief Hands over the "processor" (the right to execute) to another uthread.
 */
void ut_yield();

/**
 * @brief Initializes the uthread runtime.
 */
void ut_init();

/**
 * @brief Called by the main thread of the process (an OS thread) so that its used as the "processor" 
 * for all uthreads. All uthreads will execute in the OS thread that calls this function. 
 * The function returns when all uthreads terminate.
 */
void ut_run();

/**
 * @brief Used to cleanup the uthreads runtime.
 */
void ut_end();

#endif