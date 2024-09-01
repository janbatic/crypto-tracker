import math

millnames = ['', ' K', ' M', ' B', ' T']


def millify(n: int) -> str:
    n = float(n)
    millidx = max(0, min(len(millnames) - 1,
                         int(math.floor(0 if n == 0 else math.log10(abs(n)) / 3))))

    return '{:.3f}{}'.format(n / 10 ** (3 * millidx), millnames[millidx])


def add_currency(value: str) -> str:
    return value + " usd"