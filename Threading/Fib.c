#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

/*
    CSI3131 Assignment 2
    By: Emma Power | 300294808

    A Simple Multi-Threaded Fibonacci calulator
    Mostly to explore multi-threading in a more tangible way than in Java
*/
int a = 2;
int b = 0;
int turn = 0;

void* thread0() {

    for (int i = 0; i < a; i++) {
        while (turn != 0) {
            // Do nothing
        }

        // Critical Section

        b = b + 1;
        printf("Thread 0, B + 1 = %d \n", b);

        // Handover
        turn ++;
        turn = turn % 4;
    }
}
void* thread1() {
    for (int i = 0; i < a; i++) {
        while (turn != 1) {
            // Do nothing
        }

        // Critical Section

        b = b + 2;
        printf("Thread 1, B + 2 = %d \n", b);

        // Handover
        turn ++;
        turn = turn % 4;
    }
}
void* thread2() {
    for (int i = 0; i < a; i++) {
        while (turn != 2) {
            // Do nothing
        }

        // Critical Section

        b = b + 3;
        printf("Thread 2, B + 3 = %d \n", b);

        // Handover
        turn ++;
        turn = turn % 4;
    }
}

void* thread3() {

    for (int i = 0; i < a; i++) {
        while (turn != 3) {
            // Do nothing
        }

        // Critical Section

        b = b + 4;

        printf("Thread 3, B + 4 = %d \n", b);
        // Handover
        turn ++;
        turn = turn % 4;
    }
}

int main () {
    printf("Enter Integer A: ");
    scanf("%d", &a);
    printf("Enter Integer B: ");
    scanf("%d", &b);
    printf("Enter Thread # to start: ");
    scanf("%d", &turn);
    if (turn > 3 || turn < 0) {
        printf("invalid turn");
        return 1;
    }
    pthread_t t0, t1, t2, t3;
    pthread_create(&t0, NULL, &thread0, NULL);
    pthread_create(&t1, NULL, &thread1, NULL);
    pthread_create(&t2, NULL, &thread2, NULL);
    pthread_create(&t3, NULL, &thread3, NULL);

    // Run threads
    pthread_join(t0, NULL);
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);

    printf("Parent, B = %d \nThe Fibonacci sequence for %d is: \n", b, b);

    int v1 = 1;
    int v2 = 1;
    for (int i = 0; i < b; i++) {
        if (i < 2) {
            printf("1 ");
        }
        else {
            int temp = v1 + v2;
            v1 = v2;    
            v2 = temp;
            printf("%d ", v2);
        }
    }

    printf("\n");
    return 0;
}