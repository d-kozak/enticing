/**
 * Infers state of a combined reducer based on return types of individual reducers
 */
export type ReducerState<T> = {
    [K in keyof T]: T[K] extends (...args: any) => any ? ReturnType<T[K]> : T[K]
};