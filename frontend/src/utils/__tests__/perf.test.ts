import {PerfTimer, timedLog} from "../perf";

it('logging for loop 1_000_000', () => {
    timedLog('start');
    const startTime = performance.now();
    let count = 0;
    for (let i = 0; i < 1000000; i++) {
        count += i;
    }
    timedLog('end', startTime, 'for loop')
});

it('logging for loop 1_000_000_000', () => {
    timedLog('start');
    const startTime = performance.now();
    let count = 0;
    for (let i = 0; i < 1000000000; i++) {
        count += i;
    }
    timedLog('end', startTime, 'for loop 1_000_000_00')
});

it('perf timer', () => {
    const timer = new PerfTimer('for loop');
    let count = 0;
    for (let i = 0; i < 1000000000; i++) {
        count += i;
    }
    timer.finish();
});