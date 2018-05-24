package com.byoutline.submodulelib

import dagger.Binds
import dagger.Module
import javax.inject.Inject

/**
 * This class demonstrates that Dagger Android can use Dagger Modules that comes from "Pure Java" modules.
 */
@Module
abstract class DaggerJavaLibModule {
    @Binds
    abstract fun a(a: JavaLibDependencyImpl): JavaLibDependencyInterface
}

interface JavaLibDependencyInterface
class JavaLibDependencyImpl @Inject constructor() : JavaLibDependencyInterface