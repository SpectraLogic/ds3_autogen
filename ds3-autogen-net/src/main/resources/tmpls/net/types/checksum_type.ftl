<#include "../common/copyright.ftl" />


using System;

namespace Ds3.Models
{
    public abstract class ${name}
    {
        private static ${name} _none = new None${name}();
        private static ${name} _compute = new Compute${name}();

        public enum Type {
${netHelper.getEnumValues(enumConstants, 3)}
        }

        /// <summary>
        /// Do not provide a ${name} header on PUT.
        /// </summary>
        public static ${name} None
        {
            get { return _none; }
        }

        /// <summary>
        /// Calculate the ${name} automatically. This requires a seekable streem.
        /// </summary>
        public static ${name} Compute
        {
            get { return _compute; }
        }

        /// <summary>
        /// Provide a binary ${name} value directly, if the client
        /// application knows the ${name} of a payload beforehand.
        /// </summary>
        /// <param name="hash">The ${name} bytes</param>
        /// <returns>The ${name} "value" instance</returns>
        public static ${name} Value(byte[] hash)
        {
            return new Value${name}(hash);
        }

        /// <summary>
        /// Calls none, compute, or value, depending on which type this actually is.
        /// </summary>
        /// <param name="none">The function to call if the value is "none".</param>
        /// <param name="compute">The function to call if the value is "compute".</param>
        /// <param name="value">The function to call if the value is "value" with a ${name} payload.</param>
        public abstract void Match(Action none, Action compute, Action<byte[]> value);

        /// <summary>
        /// Calls none, compute, or value, depending on which type this actually is.
        /// </summary>
        /// <param name="none">The function to call if the value is "none".</param>
        /// <param name="compute">The function to call if the value is "compute".</param>
        /// <param name="value">The function to call if the value is "value" with a ${name} payload.</param>
        /// <returns>What either none, computer, or value return.</returns>
        public abstract T Match<T>(Func<T> none, Func<T> compute, Func<byte[], T> value);

        private ${name}()
        {
            // Prevent non-internal implementations.
        }

        private class Value${name} : ${name}
        {
            private readonly byte[] _hash;

            public Value${name}(byte[] hash)
            {
                this._hash = hash;
            }

            public override void Match(Action none, Action compute, Action<byte[]> value)
            {
                value(_hash);
            }

            public override T Match<T>(Func<T> none, Func<T> compute, Func<byte[], T> value)
            {
                return value(_hash);
            }
        }

        private class Compute${name} : ${name}
        {
            public override void Match(Action none, Action compute, Action<byte[]> value)
            {
                compute();
            }

            public override T Match<T>(Func<T> none, Func<T> compute, Func<byte[], T> value)
            {
                return compute();
            }
        }

        private class None${name} : ${name}
        {
            public override void Match(Action none, Action compute, Action<byte[]> value)
            {
                none();
            }

            public override T Match<T>(Func<T> none, Func<T> compute, Func<byte[], T> value)
            {
                return none();
            }
        }
    }
}