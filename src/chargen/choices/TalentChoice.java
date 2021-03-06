/*
 * Copyright 2017 DSATool team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package chargen.choices;

import dsa41basis.hero.Spell;
import dsa41basis.hero.Talent;
import jsonant.value.JSONObject;

public class TalentChoice extends Choice {
	public final Talent talent;
	public final int value;
	public final boolean primarySpell;

	public TalentChoice(final Talent talent, final int value, final boolean primarySpell) {
		this.talent = talent;
		this.value = value;
		this.primarySpell = primarySpell;
	}

	@Override
	public void apply(final JSONObject hero, final boolean alreadyApplied) {
		applyInternally(hero, value, false, alreadyApplied);
	}

	protected void applyInternally(final JSONObject hero, final int value, final boolean unapply, final boolean alreadyApplied) {
		if (!alreadyApplied) {
			talent.setValue((talent.getValue() == Integer.MIN_VALUE ? 0 : talent.getValue()) + value);
		}
		if (talent.getValue() == 0 && talent.getActual().getBoolOrDefault("temporary:ChoiceOnly", false)
				&& !talent.getTalent().getBoolOrDefault("Basis", false)) {
			talent.setValue(Integer.MIN_VALUE);
		}
		if (primarySpell) {
			final JSONObject actual = talent.getActual();
			if (unapply) {
				if (talent.getName().equals(actual.getStringOrDefault("temporary:PrimaryThrough", ""))) {
					actual.removeKey("temporary:PrimaryThrough");
					((Spell) talent).setPrimarySpell(false);
				}
			} else {
				((Spell) talent).setPrimarySpell(true);
				if ("".equals(actual.getStringOrDefault("temporary:PrimaryThrough", ""))) {
					actual.put("temporary:PrimaryThrough", talent.getName());
				}
			}
		}
	}

	@Override
	public void unapply(final JSONObject hero) {
		applyInternally(hero, -value, true, false);
	}
}
