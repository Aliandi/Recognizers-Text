using System.Linq;

namespace Microsoft.Recognizers.Text.DateTime
{
    /// <summary>
    /// Represents a date.
    /// </summary>
    public enum DatePeriodTimexType
    {
        /// <summary>
        /// Represents a time period by day
        /// </summary>
        ByDay,

        /// <summary>
        /// Represents a time period by week
        /// </summary>
        ByWeek,

        /// <summary>
        /// Represents time period by month
        /// </summary>
        ByMonth,

        /// <summary>
        /// Represents time period by year
        /// </summary>
        ByYear,
    }
}
