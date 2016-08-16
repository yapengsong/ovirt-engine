from otopi import util


from . import version


@util.export
def createPlugins(context):
    version.Plugin(context=context)


# vim: expandtab tabstop=4 shiftwidth=4
