// <copyright file="AssemblyInfo.cs" company="Microsoft">
// Copyright (c) PlaceholderCompany. All rights reserved.
// </copyright>

using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Microsoft.Recognizers.Definitions.Utilities
{
    public static class DefinitionLoader
    {
        public static Dictionary<Regex, Regex> LoadAmbiguityFilters(Dictionary<string, string> filters)
        {
            var ambiguityFiltersDict = new Dictionary<Regex, Regex>();

            if (filters != null)
            {
                foreach (var item in filters)
                {
                    if (!"null".Equals(item.Key))
                    {
                        ambiguityFiltersDict.Add(new Regex(item.Key, RegexOptions.Singleline), new Regex(item.Value, RegexOptions.Singleline));
                    }
                }
            }

            return ambiguityFiltersDict;
        }
    }
}
