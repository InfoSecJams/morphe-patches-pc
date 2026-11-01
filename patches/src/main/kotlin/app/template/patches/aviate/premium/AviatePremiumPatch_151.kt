/**
 * 1.5.1 (versionCode=222) Hermes-bytecode pattern set for the Aviate Premium patch.
 *
 * Bundle: assets/index.android.bundle — HBC v98, 10,064,264 bytes,
 * functionCount 28755, stringCount 76815.
 * Every pattern below was verified to appear EXACTLY ONCE in the 1.5.1 bundle
 * and every replacement is byte-length preserving (23 bytes changed in total).
 *
 * HBC v98 opcodes used:
 *   0x95 Reg8 = LoadConstTrue     0x93 Reg8 = LoadConstUndefined
 *   0x96 Reg8 = LoadConstFalse    0x76 Reg8 = Ret
 *
 * Sites (function table offsets from bundle.disasm/metadata.json):
 *   A  0x4F485D  fn #7840 hasFeatureFlag          (shared 4-byte stub, 37 callers)
 *   B  0x5919BD  fn #7841 isFeatureGated
 *   C  0x5919EF  fn #7842 isGrandfatheredFeature
 *   D  0x7014F0  fn #17029 subscription refresh callback -> no-op
 *   E  0x57F05B  fn #7504 isProUser               (AsyncStorage / widget path)
 *   F  0x7AEC4F  fn #23841 effectiveNativeAccess  (selector read by useSubscription)
 *   G  0x56AA7A  fn #6707 deriveNativeSubscriptionActive
 *
 * Resulting behaviour: isProUser()/hasFeatureAccess()/hasFeatureFlag()/
 * isFeatureGated()/isGrandfatheredFeature()/effectiveNativeAccess()/
 * deriveNativeSubscriptionActive() all return true, and the refresh callback
 * never overwrites the Pro state with a server-side "free" snapshot.
 */
package app.template.patches.aviate.premium

// A. hasFeatureAccess()/hasFeatureFlag() shared stub: false -> true.
val hasFeatureFlag_151 =
    "96 00 76 00 99 02" to
    "95 00 76 00 99 02"

// B. isFeatureGated(featureId) -> true.
val isFeatureGated_151 =
    "41 01 01 00 00 00 89 02 01 37 01 00 02 34 02 00 3B 03 02 00 44 02 03 00 87 84 01 01 8E" to
    "95 00 76 00 00 00 89 02 01 37 01 00 02 34 02 00 3B 03 02 00 44 02 03 00 87 84 01 01 8E"

// C. isGrandfatheredFeature(featureId) -> true.
val isGrandfatheredFeature_151 =
    "41 01 01 00 00 00 89 02 01 37 01 00 02 34 02 00 3B 03 02 00 44 02 03 00 87 84 01 01 8F" to
    "95 00 76 00 00 00 89 02 01 37 01 00 02 34 02 00 3B 03 02 00 44 02 03 00 87 84 01 01 8F"

// D. Subscription refresh / hydration callback -> no-op (LoadConstUndefined, Ret).
//    Without this the server snapshot ("free") overwrites the Pro state on
//    every refresh, which is why a single boolean flip is not enough.
val refreshCallbackNoop_151 =
    "89 04 02 93 00 D5 06 04 00 04 04 34" to
    "93 00 76 00 00 D5 06 04 00 04 04 34"

// E. isProUser(account) -> true (offline AsyncStorage path used by widgets).
val isProUser_151 =
    "93 03 93 06 93 00" to
    "95 00 76 00 93 00"

// F. effectiveNativeAccess() wrapper (fn #23841) -> true.
val effectiveNativeAccess_151 =
    "34 03 01 3B 02 03 09" to
    "95 00 76 00 02 03 09"

// G. deriveNativeSubscriptionActive(snapshot) -> true.
val deriveNativeSubscriptionActive_151 =
    "89 02 01 45 01 02 00 6B" to
    "95 00 76 00 01 02 00 6B"
