package com.seanshubin.interceptor

class TransformerImpl(private val transformationPatterns: List<Pair<String, String>>) : Transformer {
    private val regexAndReplaceList = transformationPatterns.map { (pattern, replacement) ->
        Regex(pattern) to replacement
    }

    override fun transform(s: String): String {
        regexAndReplaceList.forEach { (regex, replacement) ->
            if (regex.matches(s))
                return regex.replace(s, replacement)
        }
        return s
    }
}
