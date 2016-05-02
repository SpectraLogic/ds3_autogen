<#include "../common/copyright.ftl" />


using System;
using Ds3.Models;

namespace Ds3.Calls
{
    public abstract class ${name}
    {
        private static readonly JobGoneResponse _jobGone = new JobGoneResponse();

        private ${name}()
        {
            // Prevent non-internal implementations.
        }

        /// <summary>
        /// Creates a response object stating that the job has chunks available.
        /// </summary>
        /// <param name="retryAfter">The number of seconds to wait before performing the REST call again if the client already knows about all of the chunks provided.</param>
        /// <param name="jobResponse">A job response with only the chunks that are available for the client.</param>
        /// <returns>The new response instance.</returns>
        public static ${name} Success(TimeSpan retryAfter, ${responseType} jobResponse)
        {
            return new SuccessResponse(retryAfter, jobResponse);
        }

        /// <summary>
        /// Creates a response object stating that the job does not exist.
        /// </summary>
        public static ${name} JobGone
        {
            get { return _jobGone; }
        }

        /// <summary>
        /// Creates a response object stating that there are no chunks available to process.
        /// </summary>
        /// <param name="retryAfter">The amount of time to wait before asking again.</param>
        /// <returns>The new response instance.</returns>
        public static ${name} RetryAfter(TimeSpan retryAfter)
        {
            return new RetryAfterResponse(retryAfter);
        }

        /// <summary>
        /// Convenience overload if the client wants to throw a job gone exception if the job is gone.
        /// </summary>
        /// <param name="success"></param>
        /// <param name="retryAfter"></param>
        /// <returns></returns>
        public void Match(Action<TimeSpan, ${responseType}> success, Action<TimeSpan> retryAfter)
        {
            this.Match(success, () => { throw new InvalidOperationException(Resources.JobGoneException); }, retryAfter);
        }

        /// <summary>
        /// Convenience overload if the client wants to throw a job gone exception if the job is gone.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="success"></param>
        /// <param name="retryAfter"></param>
        /// <returns></returns>
        public T Match<T>(Func<TimeSpan, ${responseType}, T> success, Func<TimeSpan, T> retryAfter)
        {
            return this.Match(success, () => { throw new InvalidOperationException(Resources.JobGoneException); }, retryAfter);
        }

        /// <summary>
        /// Calls success, jobGone, or retryAfter depending on which response type this actually is.
        /// </summary>
        /// <param name="success">The function to call if the response object is a "success".</param>
        /// <param name="jobGone">The function to call if the response object is "jobGone".</param>
        /// <param name="retryAfter">The function to call if the response object is "retryAfter".</param>
        public abstract void Match(Action<TimeSpan, ${responseType}> success, Action jobGone, Action<TimeSpan> retryAfter);

        /// <summary>
        /// Calls success, jobGone, or retryAfter depending on which response type this actually is.
        /// </summary>
        /// <param name="success">The function to call if the response object is a "success".</param>
        /// <param name="jobGone">The function to call if the response object is "jobGone".</param>
        /// <param name="retryAfter">The function to call if the response object is "retryAfter".</param>
        /// <returns>What success, jobGone, or  retryAfter return.</returns>
        public abstract T Match<T>(Func<TimeSpan, ${responseType}, T> success, Func<T> jobGone, Func<TimeSpan, T> retryAfter);

        private class SuccessResponse : ${name}
        {
            private readonly TimeSpan _retryAfter;
            private readonly ${responseType} _jobResponse;

            public SuccessResponse(TimeSpan retryAfter, ${responseType} jobResponse)
            {
                this._retryAfter = retryAfter;
                this._jobResponse = jobResponse;
            }

            public override void Match(Action<TimeSpan, ${responseType}> success, Action jobGone, Action<TimeSpan> retryAfter)
            {
                success(this._retryAfter, this._jobResponse);
            }

            public override T Match<T>(Func<TimeSpan, ${responseType}, T> success, Func<T> jobGone, Func<TimeSpan, T> retryAfter)
            {
                return success(this._retryAfter, this._jobResponse);
            }
        }

        private class JobGoneResponse : ${name}
        {
            public override void Match(Action<TimeSpan, ${responseType}> success, Action jobGone, Action<TimeSpan> retryAfter)
            {
                jobGone();
            }

            public override T Match<T>(Func<TimeSpan, ${responseType}, T> success, Func<T> jobGone, Func<TimeSpan, T> retryAfter)
            {
                return jobGone();
            }
        }

        private class RetryAfterResponse : ${name}
        {
            private readonly TimeSpan _retryAfter;

            public RetryAfterResponse(TimeSpan retryAfter)
            {
                this._retryAfter = retryAfter;
            }

            public override void Match(Action<TimeSpan, ${responseType}> success, Action jobGone, Action<TimeSpan> retryAfter)
            {
                retryAfter(_retryAfter);
            }

            public override T Match<T>(Func<TimeSpan, ${responseType}, T> success, Func<T> jobGone, Func<TimeSpan, T> retryAfter)
            {
                return retryAfter(_retryAfter);
            }
        }
    }
}
