package bolts;

import dalvik.annotation.Signature;

public interface Continuation<TTaskResult, TContinuationResult> {
    TContinuationResult then(@Signature({"(", "Lbolts/Task", "<TTTaskResult;>;)TTContinuationResult;"}) Task<TTaskResult> task) throws Exception;
}
