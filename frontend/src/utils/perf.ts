export class PerfTimer {
    private startTime = performance.now();

    private messages: Array<String> = [];

    constructor(public taskName: string) {
    }

    sample(msg: string) {
        this.log(`${createTimestamp()} ${this.taskName}::${msg}, running for ${(this.sampleTime())} ms`);
    }

    finish() {
        console.log(`=================${this.taskName}==================`);
        this.messages.forEach(msg => console.log(msg));
        console.log(`${createTimestamp()} finished, total running time is ${this.sampleTime()} ms`);
        console.log('=======================================');
    }

    private sampleTime() {
        return performance.now() - this.startTime;
    }

    private log(msg: string) {
        console.log(msg);
        this.messages.push(msg);
    }
}


/**
 * Logs a message with current timestamp, optionally a durations since specific event if additional arguments are provided
 * @param msg message to log
 * @param startTime time from which the durations should be computed, optional
 * @param context name associated with the start event time, optional
 */
export function timedLog(msg: string, startTime?: number, context: string = '') {
    const timestamp = '[' + new Date().toUTCString() + '] ';
    msg = `${timestamp} ${context}:: ${msg}`;
    if (startTime) {
        console.log(`${msg}, running for ${performance.now() - startTime} ms`);
    } else {
        console.log(msg);
    }
}

export function createTimestamp(): string {
    return '[' + new Date().toUTCString() + '] ';
}