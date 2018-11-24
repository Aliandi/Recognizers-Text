# ------------------------------------------------------------------------------
# <auto-generated>
#     This code was generated by a tool.
#     Changes to this file may cause incorrect behavior and will be lost if
#     the code is regenerated.
# </auto-generated>
# ------------------------------------------------------------------------------

from .base_numbers import BaseNumbers
# pylint: disable=line-too-long
class JapaneseNumeric:
    LangMarker = ''
    DecimalSeparatorChar = '.'
    FractionMarkerToken = ''
    NonDecimalSeparatorChar = ' '
    HalfADozenText = ''
    WordSeparatorToken = ''
    RoundNumberMap = dict([('k', 1000),
                           ('m', 1000000),
                           ('g', 1000000000),
                           ('t', 1000000000000),
                           ('b', 1000000000)])
    RoundNumberMapChar = dict([('十', 10),
                               ('百', 100),
                               ('千', 1000),
                               ('万', 10000),
                               ('億', 100000000),
                               ('兆', 1000000000000)])
    ZeroToNineMap = dict([('零', 0),
                          ('一', 1),
                          ('二', 2),
                          ('三', 3),
                          ('四', 4),
                          ('五', 5),
                          ('六', 6),
                          ('七', 7),
                          ('八', 8),
                          ('九', 9),
                          ('0', 0),
                          ('1', 1),
                          ('2', 2),
                          ('3', 3),
                          ('4', 4),
                          ('5', 5),
                          ('6', 6),
                          ('7', 7),
                          ('8', 8),
                          ('9', 9),
                          ('半', 0.5)])
    FullToHalfMap = dict([('０', '0'),
                          ('１', '1'),
                          ('２', '2'),
                          ('３', '3'),
                          ('４', '4'),
                          ('５', '5'),
                          ('６', '6'),
                          ('７', '7'),
                          ('８', '8'),
                          ('９', '9'),
                          ('／', '/'),
                          ('－', '-'),
                          ('，', '\''),
                          ('、', '\''),
                          ('Ｇ', 'G'),
                          ('Ｍ', 'M'),
                          ('Ｔ', 'T'),
                          ('Ｋ', 'K'),
                          ('ｋ', 'k'),
                          ('．', '.')])
    UnitMap = dict([('万万', '億'),
                    ('億万', '兆'),
                    ('万億', '兆'),
                    (' ', '')])
    RoundDirectList = ['万', '億', '兆']
    DigitalNumberRegex = f'((?<=(\\d|\\b)){BaseNumbers.MultiplierLookupRegex}(?=\\b))'
    ZeroToNineFullHalfRegex = f'[\\d１２３４５６７８９０]'
    DigitNumRegex = f'{ZeroToNineFullHalfRegex}+'
    DozenRegex = f'.*ダース$'
    PercentageRegex = f'.+(?=パ\\s*ー\\s*セ\\s*ン\\s*ト)|.*(?=[％%])'
    DoubleAndRoundRegex = f'{ZeroToNineFullHalfRegex}+(\\.{ZeroToNineFullHalfRegex}+)?\\s*[万億]{{1,2}}(\\s*(以上))?'
    FracSplitRegex = f'[はと]|分\\s*の'
    ZeroToNineIntegerRegex = f'[一二三四五六七八九]'
    NegativeNumberTermsRegex = f'(マ\\s*イ\\s*ナ\\s*ス)'
    NegativeNumberTermsRegexNum = f'(?<!(\\d+\\s*)|[-－])[-－]'
    NegativeNumberSignRegex = f'^{NegativeNumberTermsRegex}.*|^{NegativeNumberTermsRegexNum}.*'
    SpeGetNumberRegex = f'{ZeroToNineFullHalfRegex}|{ZeroToNineIntegerRegex}|[半対]|[分厘]'
    PairRegex = '.*[対膳足]$'
    RoundNumberIntegerRegex = f'[十百千万億兆]'
    WhiteListRegex = f'(。|，|、|（|）|”｜国|週間|時間|時|匹|キロ|トン|年|個|足|本|\\s|$)'
    NotSingleRegex = f'(?<!(第|だい))(({RoundNumberIntegerRegex}+({ZeroToNineIntegerRegex}+|{ZeroToNineFullHalfRegex}+|十)\\s*))|(({ZeroToNineIntegerRegex}+|{ZeroToNineFullHalfRegex}+|十)\\s*({RoundNumberIntegerRegex}\\s*){{1,2}})\\s*(([零]?({ZeroToNineIntegerRegex}+|{ZeroToNineFullHalfRegex}+|十)\\s*{RoundNumberIntegerRegex}{{0,1}})\\s*)*\\s*(\\s*(以上)?)'
    SingleRegex = f'(({ZeroToNineIntegerRegex}|{ZeroToNineFullHalfRegex}|十)(?={WhiteListRegex}))'
    AllIntRegex = f'(((({ZeroToNineIntegerRegex}|[十百千])\\s*{RoundNumberIntegerRegex}*)|({ZeroToNineFullHalfRegex}\\s*{RoundNumberIntegerRegex})){{1,2}}(\\s*[以上])?)'
    PlaceHolderPureNumber = f'\\b'
    PlaceHolderDefault = f'\\D|\\b'
    NumbersSpecialsChars = f'(({NegativeNumberTermsRegexNum}|{NegativeNumberTermsRegex})\\s*)?{ZeroToNineFullHalfRegex}+'
    NumbersSpecialsCharsWithSuffix = f'{NegativeNumberTermsRegexNum}?{ZeroToNineFullHalfRegex}+\\s*{BaseNumbers.NumberMultiplierRegex}'
    DottedNumbersSpecialsChar = f'{NegativeNumberTermsRegexNum}?{ZeroToNineFullHalfRegex}{{1,3}}([,，、]{ZeroToNineFullHalfRegex}{{3}})+'
    NumbersWithHalfDozen = f'半({RoundNumberIntegerRegex}|(ダース))'
    NumbersWithDozen = f'{AllIntRegex}(ダース)(?!{AllIntRegex})'
    PointRegexStr = f'[\\.．]'
    AllFloatRegex = f'{NegativeNumberTermsRegex}?{AllIntRegex}\\s*{PointRegexStr}\\s*[一二三四五六七八九](\\s*{ZeroToNineIntegerRegex})*'
    NumbersWithAllowListRegex = f'{NegativeNumberTermsRegex}?({NotSingleRegex}|{SingleRegex})(?!({AllIntRegex}*([、.]{ZeroToNineIntegerRegex}+)*|{AllFloatRegex})*\\s*{PercentageRegex}+)'
    NumbersAggressiveRegex = f'(({AllIntRegex})(?!({AllIntRegex}*([、.]{ZeroToNineIntegerRegex}+)*|{AllFloatRegex})*\\s*{PercentageRegex}*))'
    PointRegex = f'{PointRegexStr}'
    DoubleSpecialsChars = f'(?<!({ZeroToNineFullHalfRegex}+[\\.．]{ZeroToNineFullHalfRegex}*))({NegativeNumberTermsRegexNum}\\s*)?{ZeroToNineFullHalfRegex}+[\\.．]{ZeroToNineFullHalfRegex}+(?!{ZeroToNineFullHalfRegex}*[\\.．]{ZeroToNineFullHalfRegex}+)'
    DoubleSpecialsCharsWithNegatives = f'(?<!({ZeroToNineFullHalfRegex}+|\\.\\.|．．))({NegativeNumberTermsRegexNum}\\s*)?[\\.．]{ZeroToNineFullHalfRegex}+(?!{ZeroToNineFullHalfRegex}*([\\.．]{ZeroToNineFullHalfRegex}+))'
    SimpleDoubleSpecialsChars = f'({NegativeNumberTermsRegexNum}\\s*)?{ZeroToNineFullHalfRegex}{{1,3}}([,，]{ZeroToNineFullHalfRegex}{{3}})+[\\.．]{ZeroToNineFullHalfRegex}+'
    DoubleWithMultiplierRegex = f'({NegativeNumberTermsRegexNum}\\s*)?{ZeroToNineFullHalfRegex}+[\\.．]{ZeroToNineFullHalfRegex}+\\s*{BaseNumbers.NumberMultiplierRegex}'
    DoubleWithThousandsRegex = f'{NegativeNumberTermsRegex}{{0,1}}\\s*({ZeroToNineFullHalfRegex}+([\\.．]{ZeroToNineFullHalfRegex}+)?\\s*[万亿萬億]{{1,2}})'
    DoubleAllFloatRegex = f'(?<!(({AllIntRegex}[.]*)|{AllFloatRegex})*){AllFloatRegex}(?!{ZeroToNineIntegerRegex}*\\s*パーセント)'
    DoubleExponentialNotationRegex = f'(?<!{ZeroToNineFullHalfRegex}+[\\.．])({NegativeNumberTermsRegexNum}\\s*)?{ZeroToNineFullHalfRegex}+([\\.．]{ZeroToNineFullHalfRegex}+)?e(([-－+＋]*[1-9１２３４５６７８９]{ZeroToNineFullHalfRegex}*)|[0０](?!{ZeroToNineFullHalfRegex}+))'
    DoubleScientificNotationRegex = f'(?<!{ZeroToNineFullHalfRegex}+[\\.．])({NegativeNumberTermsRegexNum}\\s*)?({ZeroToNineFullHalfRegex}+([\\.．]{ZeroToNineFullHalfRegex}+)?)\\^([-－+＋]*[1-9１２３４５６７８９]{ZeroToNineFullHalfRegex}*)'
    OrdinalRegex = f'(第|だい){AllIntRegex}'
    OrdinalNumbersRegex = f'(第|だい){ZeroToNineFullHalfRegex}+'
    AllFractionNumber = f'{NegativeNumberTermsRegex}{{0,1}}(({ZeroToNineFullHalfRegex}+|{AllIntRegex})\\s*[はと]{{0,1}}\\s*)?{NegativeNumberTermsRegex}{{0,1}}({ZeroToNineFullHalfRegex}+|{AllIntRegex})\\s*分\\s*の\\s*{NegativeNumberTermsRegex}{{0,1}}({ZeroToNineFullHalfRegex}+|{AllIntRegex})'
    FractionNotationSpecialsCharsRegex = f'({NegativeNumberTermsRegexNum}\\s*)?{ZeroToNineFullHalfRegex}+\\s+{ZeroToNineFullHalfRegex}+[/／]{ZeroToNineFullHalfRegex}+'
    FractionNotationRegex = f'({NegativeNumberTermsRegexNum}\\s*)?{ZeroToNineFullHalfRegex}+[/／]{ZeroToNineFullHalfRegex}+'
    PercentagePointRegex = f'(?<!{AllIntRegex})({AllFloatRegex}|{AllIntRegex})\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    SimplePercentageRegex = f'({AllFloatRegex}|{AllIntRegex}|[百])\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    NumbersPercentagePointRegex = f'({ZeroToNineFullHalfRegex})+([\\.．]({ZeroToNineFullHalfRegex})+)?\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    NumbersPercentageWithSeparatorRegex = f'({ZeroToNineFullHalfRegex}{{1,3}}[,，、]{ZeroToNineFullHalfRegex}{{3}})+([\\.．]{ZeroToNineFullHalfRegex}+)*\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    NumbersPercentageWithMultiplierRegex = f'(?<!{ZeroToNineIntegerRegex}){ZeroToNineFullHalfRegex}+[\\.．]{ZeroToNineFullHalfRegex}+\\s*{BaseNumbers.NumberMultiplierRegex}\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    FractionPercentagePointRegex = f'(?<!({ZeroToNineFullHalfRegex}+[\\.．])){ZeroToNineFullHalfRegex}+[\\.．]{ZeroToNineFullHalfRegex}+(?!([\\.．]{ZeroToNineFullHalfRegex}+))\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    FractionPercentageWithSeparatorRegex = f'{ZeroToNineFullHalfRegex}{{1,3}}([,，、]{ZeroToNineFullHalfRegex}{{3}})+[\\.．]{ZeroToNineFullHalfRegex}+\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    FractionPercentageWithMultiplierRegex = f'{ZeroToNineFullHalfRegex}+[\\.．]{ZeroToNineFullHalfRegex}+\\s*{BaseNumbers.NumberMultiplierRegex}\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    SimpleNumbersPercentageRegex = f'(?<!{ZeroToNineIntegerRegex}){ZeroToNineFullHalfRegex}+\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト(?!([\\.．]{ZeroToNineFullHalfRegex}+))'
    SimpleNumbersPercentageWithMultiplierRegex = f'(?<!{ZeroToNineIntegerRegex}){ZeroToNineFullHalfRegex}+\\s*{BaseNumbers.NumberMultiplierRegex}\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    SimpleNumbersPercentagePointRegex = f'(?!{ZeroToNineIntegerRegex}){ZeroToNineFullHalfRegex}{{1,3}}([,，]{ZeroToNineFullHalfRegex}{{3}})+\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    IntegerPercentageRegex = f'{ZeroToNineFullHalfRegex}+\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    IntegerPercentageWithMultiplierRegex = f'{ZeroToNineFullHalfRegex}+\\s*{BaseNumbers.NumberMultiplierRegex}\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    NumbersFractionPercentageRegex = f'{ZeroToNineFullHalfRegex}{{1,3}}([,，]{ZeroToNineFullHalfRegex}{{3}})+\\s*パ\\s*ー\\s*セ\\s*ン\\s*ト'
    SimpleIntegerPercentageRegex = f'{NegativeNumberTermsRegexNum}?{ZeroToNineFullHalfRegex}+([\\.．]{ZeroToNineFullHalfRegex}+)?(\\s*)[％%]'
    NumbersFoldsPercentageRegex = f'{ZeroToNineFullHalfRegex}(([\\.．]?|\\s*){ZeroToNineFullHalfRegex})?\\s*[の]*\\s*割引'
    FoldsPercentageRegex = f'{ZeroToNineIntegerRegex}(\\s*[.]?\\s*{ZeroToNineIntegerRegex})?\\s*[の]\\s*割引'
    SimpleFoldsPercentageRegex = f'{ZeroToNineFullHalfRegex}\\s*割(\\s*(半|({ZeroToNineFullHalfRegex}\\s*分\\s*{ZeroToNineFullHalfRegex}\\s*厘)|{ZeroToNineFullHalfRegex}))?'
    SpecialsPercentageRegex = f'({ZeroToNineIntegerRegex}|[十])\\s*割(\\s*(半|{ZeroToNineIntegerRegex}))?'
    NumbersSpecialsPercentageRegex = f'({ZeroToNineFullHalfRegex}[\\.．]{ZeroToNineFullHalfRegex}|[1１][0０])\\s*割'
    SimpleSpecialsPercentageRegex = f'{ZeroToNineIntegerRegex}\\s*[.]\\s*{ZeroToNineIntegerRegex}\\s*割'
    SpecialsFoldsPercentageRegex = f'半\\s*分|(?<=ダース)'
    TillRegex = f'(から|--|-|—|——|~)'
    MoreRegex = f'(大なり|大きい|高い|大きく|>)'
    LessRegex = f'(小なり|小さい|低い|<)'
    EqualRegex = f'(等しい|イコール|=)'
    MoreOrEqual = f'((大なりかイコール)|(大きいかイコール)|(大なりか等しい)|(大きいか等しい)|小さくない|以上|最低)'
    MoreOrEqualSuffix = f'(より(大なりイコール|小さくない))'
    LessOrEqual = f'(({LessRegex}\\s*(或|或者)?\\s*{EqualRegex})|(小なりかイコール)|(小なりか等しい)|(小さいかイコール)|(小さいか等しい)|(小さいか等しい)|大さくない|以下|最大)'
    LessOrEqualSuffix = f'(小なりイコール|大さくない)'
    OneNumberRangeMoreRegex1 = f'(?<number1>((?!(((，|、)(?!\\d+))|((,|、)(?!\\d+))|。)).)+)\\s*((より)\\s*(({MoreOrEqual}|{MoreRegex}))|超える|を超える)'
    OneNumberRangeMoreRegex2 = f'(?<number1>((?!((，|、(?!\\d+))|(,|、(?!\\d+))|。)).)+)\\s*(より)?(大なり)'
    OneNumberRangeMoreRegex3 = f'(?<number1>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*(以上|最低)(?![万億]{{1,2}})'
    OneNumberRangeMoreRegex4 = f'({MoreOrEqual}|{MoreRegex})\\s*(?<number1>((?!(と|は|((と)?同時に)|((と)?そして)|が|，|、|,|(，(?!\\d+))|(,(?!\\d+))|。)).)+)'
    OneNumberRangeMoreSeparateRegex = f'^[.]'
    OneNumberRangeLessSeparateRegex = f'^[.]'
    OneNumberRangeLessRegex1 = f'(?<number2>((?!(((，|、)(?!\\d+))|((,|、)(?!\\d+))|。)).)+)\\s*(より)\\s*({LessOrEqual}|{LessRegex})'
    OneNumberRangeLessRegex2 = f'(?<number2>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*(より)?(小な)'
    OneNumberRangeLessRegex3 = f'(?<number2>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*(以下|未満)(?![万億]{{1,2}})'
    OneNumberRangeLessRegex4 = f'({LessOrEqual}|{LessRegex})\\s*(?<number2>((?!(と|は|((と)?同時に)|((と)?そして)|が|，|、|,|(，(?!\\d+))|(,(?!\\d+))|。)).)+)'
    OneNumberRangeEqualRegex = f'(((?<number1>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*(に)\\s*{EqualRegex})|({EqualRegex}\\s*(?<number1>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)))'
    TwoNumberRangeRegex1 = f'(?<number1>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*(と|{TillRegex})\\s*(?<number2>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*(の間)'
    TwoNumberRangeRegex2 = f'({OneNumberRangeMoreRegex1}|{OneNumberRangeMoreRegex2}|{OneNumberRangeMoreRegex3}|{OneNumberRangeMoreRegex4})\\s*(と|は|((と)?同時に)|((と)?そして)|が|，|、|,)?\\s*({OneNumberRangeLessRegex1}|{OneNumberRangeLessRegex2}|{OneNumberRangeLessRegex3}|{OneNumberRangeLessRegex4})'
    TwoNumberRangeRegex3 = f'({OneNumberRangeLessRegex1}|{OneNumberRangeLessRegex2}|{OneNumberRangeLessRegex3}|{OneNumberRangeLessRegex4})\\s*(と|は|((と)?同時に)|((と)?そして)|が|，|、|,)?\\s*({OneNumberRangeMoreRegex1}|{OneNumberRangeMoreRegex2}|{OneNumberRangeMoreRegex3}|{OneNumberRangeMoreRegex4})'
    TwoNumberRangeRegex4 = f'(?<number1>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)\\s*{TillRegex}\\s*(?<number2>((?!((，(?!\\d+))|(,(?!\\d+))|。)).)+)'
    AmbiguousFractionConnectorsRegex = f'^[.]'
# pylint: enable=line-too-long