from django.contrib import admin

from django.contrib import admin
from cryptotracker.models import Crypto
from cryptotracker.models import Portfolio
from cryptotracker.models import BrokerPairs
from cryptotracker.models import Broker


#

# Register your models here.
@admin.register(Crypto)
class CryptoModel(admin.ModelAdmin):
    model = Crypto

    list_display = (
        'user',
        'name',
        'amount',
    )
    search_fields = [
        'user',
        'name',

    ]


@admin.register(Portfolio)
class PortfolioModel(admin.ModelAdmin):
    model = Portfolio

    list_display = (
        'user',
        'timestamp',
        'amount_value',
    )
    search_fields = [
        'user',
    ]


@admin.register(BrokerPairs)
class BrokerPairsModel(admin.ModelAdmin):
    model = BrokerPairs

    list_display = (
        'broker',
        'usdt_pair',
    )
    search_fields = [
        'broker',
    ]


@admin.register(Broker)
class BrokerModel(admin.ModelAdmin):
    model = Broker

    list_display = (
        'name',
    )
    search_fields = [
        'name',
    ]
